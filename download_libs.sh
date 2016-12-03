# ライブラリのjarのダウンロード、存在する場合はスキップ
jars=(SRClientHelper.jar core-2.2.jar javase-2.2.jar jna-4.1.0.jar opencv-310.jar)
download_count=0
for jar in ${jars[@]}; do
  if [ ! -e lib/${jar} ]; then
    wget https://github.com/vstoneofficial/SotaSample/blob/master/lib/${jar}?raw=true -O lib/${jar}
    download_count=`expr $download_count + 1`
  else
    echo -e "${jar}     \tis already exists."
  fi
done
echo -e "\n${download_count} files downloaded."
