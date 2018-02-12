@echo off
%1 mshta vbscript:createobject("wscript.shell").run("""%~0"" :",0)(window.close)&&exit

cd /d C:\Users\Administrator\Desktop\deploy-IBoss
start /b java -jar IBoss-0.0.1-SNAPSHOT.jar