package com.guina.loratracker.action;

import java.io.DataOutputStream;
import java.io.IOException;
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

@ManagedBean
@ViewScoped
public class TrackerManager implements Serializable
{
	private static final long serialVersionUID = 4932054294380372680L;
	private Logger logger = Logger.getLogger(TrackerManager.class);
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

			LoraCommand heartBeat = new LoraCommand(LoraCommandType.HEART_BEAT, "CSHeart");
			logger.info("Send Command: " + heartBeat);
			outToServer.write(getCommandData(heartBeat));
			LoraCommand readFromServer = readData(inputStream);
			if (!LoraCommandType.HEART_BEAT.equals(readFromServer.getCommandType()))
			{
				logger.error("Unexpected command received: " + readFromServer);
			}

			LoraCommand login = new LoraCommand(LoraCommandType.JASON_OBJ, loginInfo);
			logger.info("Send Command: " + login);
			byte[] loginInfoBuff = getCommandData(login);
			outToServer.write(loginInfoBuff);
			readFromServer = readData(inputStream);
			if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
			{
				throw new RuntimeException("Unexpected command received: " + readFromServer);
			}
			LoraLoginJsn receivedLoginInfo = JsonUtils.convertStringToJson(LoraLoginJsn.class,
							readFromServer.getData());
			logger.info("Successfully logged in." + receivedLoginInfo);
			while(true)
			{
				readFromServer = readData(inputStream);
				if (!LoraCommandType.JASON_OBJ.equals(readFromServer.getCommandType()))
				{
					throw new RuntimeException("Unexpected command received: " + readFromServer);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error to connect to lora application server.", e);
		}

		return null;
	}

	private byte[] getCommandData(LoraCommand command)
	{
		int length = command.getData().length();
		logger.info("Login info length: " + length);
		byte[] loginInfoBuff = new byte[length + 8];
		loginInfoBuff[0] = (byte) 0xB0;
		loginInfoBuff[1] = (byte) 0xB0;
		loginInfoBuff[2] = (byte) 0xB0;
		loginInfoBuff[3] = (byte) 0xB0;

		// command
		LoraCommandType com = command.getCommandType();
		if (LoraCommandType.HEART_BEAT.equals(com))
		{
			loginInfoBuff[4] = (byte) 0xE0;
		}
		else if (LoraCommandType.JASON_OBJ.equals(com))
		{
			loginInfoBuff[4] = (byte) 0xE1;
		}
		else
		{
			// unexpected command
			throw new RuntimeException("Unexpected command: " + com);
		}

		// Length, Little endian
		loginInfoBuff[5] = (byte) (length & 0xFF);
		loginInfoBuff[6] = (byte) (length >> 8 & 0xFF);

		byte[] loginInfoArr = command.getData().getBytes();
		for (int idx = 0; idx < length; idx++)
		{
			loginInfoBuff[idx + 7] = loginInfoArr[idx];
		}
		loginInfoBuff[length + 7] = (byte) 0xF0;// Ending

		return loginInfoBuff;
	}

	private LoraCommand readData(InputStream inputStream) throws IOException
	{
		LoraCommand commandReceived = new LoraCommand();
		byte[] buff = new byte[1024];
		while (true)
		{
			// find start id
			if (inputStream.read(buff, 0, 7) < 0)
			{
				continue;
			}

			for (int idx = 0; idx < 4; idx++)
			{
				if (!(buff[0] == (byte) 0xB0))
				{
					logger.error("unexpected start ID: " + String.valueOf(buff));
					continue;
				}
			}

			// command
			// data of heart beat
			if (buff[4] == (byte) 0xE0)
			{
				commandReceived.setCommandType(LoraCommandType.HEART_BEAT);
			}
			// JSON object
			else if (buff[4] == (byte) 0xE1)
			{
				commandReceived.setCommandType(LoraCommandType.JASON_OBJ);
			}
			else
			{
				// unexpected command
				logger.error("Unexpected command: " + String.valueOf((byte) buff[4]));
				continue;
			}
			logger.info("Received command type: " + commandReceived.getCommandType());

			// Little endian
			int payloadLen = ((buff[6] & 0xff) << 8) | (buff[5] & 0xff);
			logger.info("Received data length: " + payloadLen);
			// Pay load data
			if (inputStream.read(buff, 7, payloadLen) < 0)
			{
				logger.error("No data found: " + String.valueOf(buff));
				continue;
			}

			String data = "";
			for (int idx = 7; idx < payloadLen + 7; idx++)
			{
				data += String.valueOf((char) buff[idx]);
			}
			commandReceived.setData(data);

			inputStream.read(buff, 7 + payloadLen, 1);
			if (buff[7 + payloadLen] != (byte) 0xF0)
			{
				logger.error("unexpected end of data: " + buff[7 + payloadLen]);
			}
			break;
		}
		logger.info("Received command: " + commandReceived);

		return commandReceived;
	}

	public static String bytesToHex(byte[] in)
	{
		final StringBuilder builder = new StringBuilder();
		for (byte b : in)
		{
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
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
