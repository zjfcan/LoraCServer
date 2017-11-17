package com.guina.loratracker.service;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timer;

import org.jboss.logging.Logger;

import com.guina.loratracker.model.ApplicationData;
import com.guina.loratracker.model.GateWayRx;
import com.guina.loratracker.model.Gateway;
import com.guina.loratracker.model.LoginAccount;
import com.guina.loratracker.model.LoraCommand;
import com.guina.loratracker.model.LoraCommandType;
import com.guina.loratracker.model.LoraLoginJsn;
import com.guina.loratracker.model.Mote;
import com.guina.loratracker.model.UpLinkData;
import com.guina.loratracker.util.EncodeUtil;
import com.guina.loratracker.util.JsonUtils;
import com.guina.loratracker.util.LoraUtil;

@Singleton
public class LoraDataRetriever
{
	private Logger logger = Logger.getLogger(this.getClass());
	public static final String HEARTBEAT_SUCCEEDED = "Heart beat succeeded!";
	public static final String HEART_BEAT_FAILED = "Heart beat failed!";
	public static final String LOGGIN_SUCESS = "Successfully logged in.";
	public static final String LOGGIN_FAILED = "Successfully logged in.";
	// public static final String TCP_SERVER_ADDRESS = "iotcn01.manthink.cn";
	public static final String TCP_SERVER_ADDRESS = "101.201.114.220";
	private static final int TCP_PORT = 6001;
	private static final String user = "testaccount";
	private static final String pwd = "testaccount";
	private String heartBeatMessage;
	private String loginMessage;

	@EJB
	private LoraDataManager dataManager;

	@SuppressWarnings("unused")
	// @Schedule(second = "*/15", minute = "*/1", hour = "*", /* "8-23", dayOfWeek = "Mon-Fri", */dayOfMonth = "*", month = "*", year = "*", persistent = false,
	// info = "ConnectionTimer")
	private void scheduledTimeout(final Timer t)
	{
		startReceiveData(50);
	}

	public void startReceiveData(int total)
	{
		logger.info("Connecting server (with count=" + total + ")...");
		String loginInfo = createLoginRequest();

		try (Socket clientSocket = new Socket(TCP_SERVER_ADDRESS, TCP_PORT))
		{
			OutputStream outputStreamToServer = clientSocket.getOutputStream();
			DataOutputStream outToServer = new DataOutputStream(outputStreamToServer);
			InputStream inputStream = clientSocket.getInputStream();

			// Heart beat
			LoraCommand heartBeat = new LoraCommand(LoraCommandType.HEART_BEAT, "CSHeart");
			logger.info("Send Command: " + heartBeat);
			outToServer.write(LoraUtil.getCommandData(heartBeat));
			LoraCommand readFromServer = LoraUtil.readData(inputStream);
			if (!LoraCommandType.HEART_BEAT.equals(readFromServer.getCommandType()))
			{
				setHeartBeatMessage(HEART_BEAT_FAILED);
				logger.error(HEART_BEAT_FAILED + " Unexpected command received: " + readFromServer);
				return;
			}
			setHeartBeatMessage(HEARTBEAT_SUCCEEDED);
			logger.info(HEARTBEAT_SUCCEEDED);

			// login
			LoraCommand login = new LoraCommand(LoraCommandType.JASON_OBJ, loginInfo);
			logger.info("Send Command: " + login);
			byte[] loginInfoBuff = LoraUtil.getCommandData(login);
			outToServer.write(loginInfoBuff);
			readFromServer = LoraUtil.readData(inputStream);
			if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
			{
				setLoginMessage(LOGGIN_FAILED);
				logger.error(LOGGIN_FAILED + " Unexpected command received: " + readFromServer);
				return;
			}

			ApplicationData loginDataReceived = JsonUtils.convertStringToJson(ApplicationData.class,
							readFromServer.getData());
			if (loginDataReceived == null || loginDataReceived.getAccount() == null
							|| !loginDataReceived.getAccount().isAccept())
			{
				setLoginMessage(LOGGIN_FAILED);
				logger.error(LOGGIN_FAILED + " Server did not accept: " + readFromServer);
				return;
			}

			setLoginMessage(LOGGIN_SUCESS);
			// LoraLoginJsn receivedLoginInfo = JsonUtils.convertStringToJson(LoraLoginJsn.class,
			// readFromServer.getData());
			// logger.info(LOGGIN_SUCESS + " " + receivedLoginInfo);

			// Read data
			int count = 0;
			while (count++ < total)
			{
				readFromServer = LoraUtil.readData(inputStream);
				if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
				{
					logger.error("Unexpected command received: " + readFromServer);
					Thread.sleep(60000);
					continue;
				}

				ApplicationData dataReceived = JsonUtils.convertStringToJson(ApplicationData.class,
								readFromServer.getData());
				if (dataReceived != null)
				{
					UpLinkData immeApp = dataReceived.getImmeApp();
					String logMsg = "Received (" + count + "): " + readFromServer;
					if (immeApp != null)
					{
						logger.info(logMsg);
						recordGatewayMotes(immeApp);
					}

					UpLinkData app = dataReceived.getApp();
					if (app != null)
					{
						logger.info(logMsg);
						recordGatewayMotes(app);
					}

					Mote mote = dataReceived.getMote();
					if (mote != null)
					{
						logger.info(logMsg);
						dataManager.putEuiToMote(LoraUtil.longToHex(mote.getEui()), mote);
					}

					Gateway gateway = dataReceived.getGateway();
					if (gateway != null && gateway.getStatus().getLati() != 0)
					{
						logger.info(logMsg);
						dataManager.putEuiToGateways(LoraUtil.longToHex(gateway.getEUI()), gateway);
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error to connect to lora application server.", e);
		}
	}

	private void recordGatewayMotes(UpLinkData userMote)
	{
		Iterator<GateWayRx> ite = userMote.getGwrx().iterator();
		while (ite.hasNext())
		{
			GateWayRx gateway = ite.next();
			String gatewayEuiHex = LoraUtil.longToHex(gateway.getEui());
			Map<String, Set<String>> gatewayEuiToMoteEui = dataManager.getGatewayEuiToMoteEui();
			Set<String> moteEuis = gatewayEuiToMoteEui.get(gatewayEuiHex);
			if (moteEuis == null)
			{
				moteEuis = new HashSet<>();
				dataManager.putGatewayEuiToMoteEui(gatewayEuiHex, moteEuis);
			}
			String moteEuiHex = LoraUtil.longToHex(userMote.getMoteEui());
			moteEuis.add(moteEuiHex);

			Set<String> moteReportTimes = dataManager.getMoteEuiToMoteReportTime().get(moteEuiHex);
			if (moteReportTimes == null)
			{
				moteReportTimes = new HashSet<>();
				dataManager.putMoteEuiToMoteReportTime(moteEuiHex, moteReportTimes);
			}
			String sentGatewayTime = String.valueOf(userMote.getUserData().getSeqno()) + "_"
							+ gatewayEuiHex; // because gateway.getTime() sometimes is not available(give null);
			moteReportTimes.add(sentGatewayTime);

			dataManager.putReportTimeToUplinkData(sentGatewayTime, userMote);
		}
	}

	private String createLoginRequest()
	{
		// String loginInfo = "{" + "\"account\":{" + "\"user\":\""+user+"\"," + "\"APPEUI\":0,"
		// + "\"customer\":null," + "\"pwd\":\""
		// + EncodeUtil.md5Encrypt32(pwd) + "\"," + "\"DwnData\":true,"
		// + "\"accept\":false," + "\"serverAccept\":false," + "\"serverUser\":null,"
		// + "\"serverPwd\":null," + "\"serverDB\":null" + "}" + "}";

		LoraLoginJsn loginData = new LoraLoginJsn();
		LoginAccount account = new LoginAccount();
		account.setUser(user);
		account.setPwd(EncodeUtil.md5Encrypt32(pwd));
		account.setDwnData(true);
		loginData.setAccount(account);

		String loginInfo = JsonUtils.convertJsonToString(loginData);

		return loginInfo;
	}

	public String getHeartBeatMessage()
	{
		return heartBeatMessage;
	}

	public void setHeartBeatMessage(String heartBeatMessage)
	{
		this.heartBeatMessage = heartBeatMessage;
	}

	public String getLoginMessage()
	{
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage)
	{
		this.loginMessage = loginMessage;
	}
}
