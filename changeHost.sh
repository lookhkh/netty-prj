echo "172.217.27.13 accounts.google.com" >> /etc/hosts \n
echo "142.250.66.42 oauth2.googleapis.com" >> /etc/hosts \n
echo "172.217.31.138 www.googleapis.com" >> /etc/hosts \n
echo "216.239.36.55 fcm.googleapis.com" >> /etc/hosts \n

java -jar -Dspring.http.encoding.charset=UTF-8 -Dspring.http.encodingnabled=true -Dspring.http.encoding.force=true -Dfile.encoding=UTF-8 -Dspring.profiles.active=local /home/netty/app.jar