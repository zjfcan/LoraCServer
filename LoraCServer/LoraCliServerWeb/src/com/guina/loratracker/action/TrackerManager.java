package com.guina.loratracker.action;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import com.guina.loratracker.entity.LoraUser;
import com.guina.loratracker.model.LoginAccount;
import com.guina.loratracker.model.LoraLoginJsn;
import com.guina.loratracker.service.LoraDbService;
import com.guina.loratracker.util.EncodeUtil;
import com.guina.loratracker.util.JsonUtils;
import com.guina.loratracker.util.LoraUtil;

@ManagedBean
@ViewScoped
public class TrackerManager implements Serializable
{
	private static final long serialVersionUID = 4932054294380372680L;
	private Logger logger = Logger.getLogger(this.getClass());
	// public static final String TCP_SERVER_ADDRESS = "iotcn01.manthink.cn";
	public static final String TCP_SERVER_ADDRESS = "101.201.114.220";
	private static final int TCP_PORT = 6001;
	private static final String user = "testaccount";
	private static final String pwd = "testaccount";

	@EJB
	private LoraDbService dbService;

	private LoraUser loraUser;

	@PostConstruct
	public void init()
	{
		loraUser = new LoraUser();
	}

	public String connectServer()
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

			//Heart beat
			LoraCommand heartBeat = new LoraCommand(LoraCommandType.HEART_BEAT, "CSHeart");
			logger.info("Send Command: " + heartBeat);
			outToServer.write(LoraUtil.getCommandData(heartBeat));
			LoraCommand readFromServer = LoraUtil.readData(inputStream);
			if (!LoraCommandType.HEART_BEAT.equals(readFromServer.getCommandType()))
			{
				logger.error("Unexpected command received: " + readFromServer);
			}

			//login
			LoraCommand login = new LoraCommand(LoraCommandType.JASON_OBJ, loginInfo);
			logger.info("Send Command: " + login);
			byte[] loginInfoBuff = LoraUtil.getCommandData(login);
			outToServer.write(loginInfoBuff);
			readFromServer = LoraUtil.readData(inputStream);
			if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
			{
				throw new RuntimeException("Unexpected command received: " + readFromServer);
			}
			LoraLoginJsn receivedLoginInfo = JsonUtils.convertStringToJson(LoraLoginJsn.class,
							readFromServer.getData());
			logger.info("Successfully logged in." + receivedLoginInfo);
			
			//Read data
			int count = 0;
			while(true)
			{
				readFromServer = LoraUtil.readData(inputStream);
				if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
				{
					throw new RuntimeException("Unexpected command received: " + readFromServer);
				}
				
				if (count++>15)
				{
					break;
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error to connect to lora application server.", e);
		}

		return null;
	}

	public String saveUser()
	{
		dbService.saveUser(loraUser);

		return null;
	}

	public LoraUser getLoraUser()
	{
		return loraUser;
	}

	public void setLoraUser(LoraUser loraUser)
	{
		this.loraUser = loraUser;
	}
}
