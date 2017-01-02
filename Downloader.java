package com.att.validation;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/*
 * 
 * Download files from server 
 * Author: Vivek
 * First need tunnel in order to download file directly from ATT Stagging need to run below command
 * scp vv200v@hht029b2.hydc.sbc.com:/home/doxuser/m84273/MOB_UV/OMS/1610/EF/EF_49354_Rollback.sql /projecthome/oms/cc/epcdev1/vivek;
 * 
 */

public class Downloader
{

	public static void main(String[] args)
	{
		boolean isFileDownloaded = true;
		boolean success = false;
		boolean isRemove = false;
		try
		{
			JSch ssh = new JSch();
			Session session = ssh.getSession("epcdev1", "stllin100", 22);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword("Unix11!");

			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("Connection Established");

			ChannelSftp sftp = (ChannelSftp) channel;

			sftp.cd("/projecthome/oms/cc/epcdev1/vivek/");
			System.out.println("Reading path from server...");
			if (isFileDownloaded)
			{

				sftp.get("/projecthome/oms/cc/epcdev1/vivek/EF_49354_Rollback.sql", "E://Downloads//EF_49354_Rollback.sql");
				success = true;
				isRemove = true;
				if (success)
					System.out.println("File Downloaded Successfully will start deleting file from stllin100");
			}
			if (isRemove)
			{
				sftp.rm("/projecthome/oms/cc/epcdev1/vivek/EF_49354_Rollback.sql");
				System.out.println("File Deleted From stllin100");
			}
			channel.disconnect();
			session.disconnect();

			System.out.println("Connection Closed");
		}
		catch (JSchException e)
		{
			System.out.println(e.getMessage().toString());
			e.printStackTrace();
		}
		catch (SftpException e)
		{
			System.out.println(e.getMessage().toString());
			e.printStackTrace();
		}
	}
}
