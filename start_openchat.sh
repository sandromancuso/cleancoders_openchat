LOG_FILE=openchat.log

echo "Starting system" > $LOG_FILE

echo "Invoking cmd: mvn exec:java -Dexec.mainClass=org.openchat.OpenChatLauncher" >> $LOG_FILE
mvn exec:java -Dexec.mainClass=org.openchat.OpenChatLauncher >> $LOG_FILE 2>>$LOG_FILE &
echo "System process started" >> $LOG_FILE
echo $! > openchat.pid

echo "Waiting openchat to launch on port 4321..."

while ! nc -z localhost 4321; do
  sleep 0.1 # wait for 1/10 of the second before check again
done

echo "Openchat launched"
