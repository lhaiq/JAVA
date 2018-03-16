#!/usr/bin/python
import paramiko
import sys
import os
import os.path

ip = "120.27.49.106"
user = "root"
passwd = "JXdB1=nm"


print "clean..."
os.system("gradle clean ")
print "build...."
os.system("gradle build -x test ")
print "unzip.."
os.system("unzip bhyy-webapp/build/libs/bhyy-webapp-0.0.1-SNAPSHOT.war -d bhyy-webapp/build/libs/")


ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect(ip, 22, user, passwd, timeout=5)
stdin, stdout, stderr = ssh.exec_command("/home/tomcat/bin/shutdown.sh")
print stdout.readlines()

sftp = ssh.open_sftp()

print "upload file"
sftp.put("bhyy-webapp/build/libs/WEB-INF/web.xml", "/home/tomcat/webapps/ROOT/WEB-INF/web.xml")
sftp.put("bhyy-webapp/build/libs/WEB-INF/mvc-servlet.xml", "/home/tomcat/webapps/ROOT/WEB-INF/mvc-servlet.xml")
sftp.put("bhyy-webapp/build/libs/WEB-INF/lib/bhyy-service-api-0.0.1-SNAPSHOT.jar"
         , "/home/tomcat/webapps/ROOT/WEB-INF/lib/bhyy-service-api-0.0.1-SNAPSHOT.jar")
sftp.put("bhyy-webapp/build/libs/WEB-INF/lib/bhyy-service-0.0.1-SNAPSHOT.jar"
         , "/home/tomcat/webapps/ROOT/WEB-INF/lib/bhyy-service-0.0.1-SNAPSHOT.jar")



print "upload class"

ssh.exec_command("rm -rf /home/tomcat/webapps/ROOT/WEB-INF/classes/*")
os.system("scp -r bhyy-webapp/build/libs/WEB-INF/classes/* root@120.27.49.106:/home/tomcat/webapps/ROOT/WEB-INF/classes/")


# restart
stdin, stdout, stderr = ssh.exec_command("/home/tomcat/bin/startup.sh")
print stdout.readlines()
print stderr.readlines()
