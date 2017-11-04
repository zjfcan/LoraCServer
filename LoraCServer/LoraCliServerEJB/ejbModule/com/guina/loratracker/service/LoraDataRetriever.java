package com.guina.loratracker.service;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import org.jboss.logging.Logger;

import com.guina.loratracker.model.LoraCommand;
import com.guina.loratracker.model.LoraCommandType;
import com.guina.loratracker.model.ApplicationData;
import com.guina.loratracker.model.LoginAccount;
import com.guina.loratracker.model.LoraLoginJsn;
import com.guina.loratracker.util.EncodeUtil;
import com.guina.loratracker.util.JsonUtils;
import com.guina.loratracker.util.LoraUtil;

@Stateless
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

	/**
	 * Default constructor.
	 */
	public LoraDataRetriever()
	{
		// TODO Auto-generated constructor stub
	}

	// @SuppressWarnings("unused")
	@Schedule(second = "*/15", minute = "*", hour = "*", /* "8-23", dayOfWeek = "Mon-Fri", */dayOfMonth = "*", month = "*", year = "*", persistent = false, info = "ConnectionTimer")
	private void scheduledTimeout(final Timer t)
	{
		connectServer();
	}

	public void connectServer()
	{
		logger.info("Connect server: ");
		LoraLoginJsn loginData = new LoraLoginJsn();
		LoginAccount account = new LoginAccount();
		account.setUser(user);
		account.setPwd(EncodeUtil.md5Encrypt32(pwd));
		account.setDwnData(true);
		loginData.setAccount(account);

		String loginInfo = JsonUtils.convertJsonToString(loginData);

		// String loginInfo = "{" + "\"account\":{" + "\"user\":\""+user+"\"," + "\"APPEUI\":0,"
		// + "\"customer\":null," + "\"pwd\":\""
		// + EncodeUtil.md5Encrypt32(pwd) + "\"," + "\"DwnData\":true,"
		// + "\"accept\":false," + "\"serverAccept\":false," + "\"serverUser\":null,"
		// + "\"serverPwd\":null," + "\"serverDB\":null" + "}" + "}";

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
			if (loginDataReceived.getAccount() == null
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
			while (true)
			{
				readFromServer = LoraUtil.readData(inputStream);
				if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
				{
					logger.error("Unexpected command received: " + readFromServer);
					continue;
				}
				ApplicationData dataReceived = JsonUtils.convertStringToJson(ApplicationData.class,
								readFromServer.getData());

				if (dataReceived != null)
				{
					if (dataReceived.getImmeApp() != null)
					{
						logger.info("Received 'immeApp'");
					}

					if (dataReceived.getApp() != null)
					{
						logger.info("Received 'app'");
					}

					if (dataReceived.getMote() != null)
					{
						logger.info("Received 'mote'");
					}

					if (dataReceived.getGateway() != null)
					{
						logger.info("Received 'gateway'");
					}
				}
				if (count++ > 10)
				{
					break;
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error to connect to lora application server.", e);
		}
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