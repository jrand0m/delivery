#!/bin/sh
name="delivery_master"
ext=".tar.gz"
file_name="$name$ext"
url="https://ubi.dyndns.info/delivery/$file_name"
cert="/opt/installed/ciphers/clientkey"
tmp="/tmp/mew/$name/"
dest="/opt/srv/$name/"
backup_dir="/opt/bkp/"
echo ">>> Starting ..."
if [ -z "$PLAY_HOME" ]
then
	echo ">>> PLAY_HOME var is missing";
	exit 1;
fi
#cleaning tmp dir
if [ -d "$tmp" ] 
then
	rm -rf $tmp;
fi
mkdir -p $tmp
cd $tmp
echo ">>> getting sha1 sum"
wget -q --no-check-certificate --certificate=$cert $url.sha1
ret=$? 
if [ $ret -ne 0 ] 
then
	
	echo ">>> failed to get file $url.sha1"; 
	echo ">>> return code is $ret";
	exit 1;
fi

if diff $file_name.sha1 $backup_dir$file_name.sha1 >/dev/null ; then
  echo ">>> No new version found";
  exit 0;
else
  echo ">>> new version found!";
fi

wget -q --no-check-certificate --certificate=$cert $url
ret=$? 
if [ $ret -ne 0 ] 
then
	echo ">>> failed to get file $url"; 
	echo ">>> return code is $ret";
	exit 1;
fi
sha1sum -c $file_name.sha1
ret=$? 
if [ $ret -ne 0 ] 
then
	echo ">>> SHA1 check failed !"; 
	echo ">>> return code is $ret";
	exit 1;
fi
tar -xzf $tmp$file_name
ret=$?
if [ $ret -ne 0 ] 
then
	
	echo ">>> failed to unpack $tmp$file_name"; 
	echo ">>> return code is $ret";
	exit 1;
fi
if [ ! -d  "$tmp$name" ]
then
	echo ">>> maybe bad structure";
	exit 1;
fi
$PLAY_HOME/play stop $dest
zip -r $backup_dir`date +%Y%m%d%k%M`-$name.zip $dest
rm -rf `find . -type d | egrep -v 'public/static.*$' | grep -v 'public$'`
cp -r "$tmp$name" "$dest"
$PLAY_HOME/play start "$dest" -Dprecompiled=true --%prod
rm -rf $tmp

##TBD Backup and rescue
