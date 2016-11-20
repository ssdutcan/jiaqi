#!/bin/ksh

SysDate=`date +%Y%m%d`

WLUser1=wl_cnaps
WLUser2=wl_cnaps
WLIP1=11.170.200.1
WLIP2=11.170.200.3
WLPort=21
LocalPath=$HOME/data/CMT/$SysDate/
FileName=CMT.RES.$SysDate
CMTFILE=$/HOME/data/CMT/$SysData/CMT.RES.$SysDate

CreateDirectory()
{
	if [ ! -d $1 ]
	then
		mkdir -p $1
		if [ $? -ne 0 ]
		then
			echo "����Ŀ¼[$1]ʧ��"
			exit -1
		fi
	fi
}

GetCMTFileFromWL()
{
	RemotePath=/home/ap/$2/data/CMT/

ftp -iv -n $1 $3 <<! 2>&1
use $2 $2
bin
lcd $LocalPath
cd $RemotePath
get $FileName
by
!

	mv $CMTFile $CMTFile.$1
	if [ $? -ne 0 ]
	then
		echo "�ƶ��ļ�[$CMTFile]��[$CMTFile.$1]ʧ��"
		exit -1
	fi
}

CombineCMTFile()
{
	echo "֧��;��|@|ϵͳ��������|@|�������к�|@����������|@|�������˺�|@|�տ����к�|@|�տ�������|@|�տ����˺�|@|���|@|����" > $CMTFile.bak
	
	cat $CMTFile.bak $CMTFile.$WLIP1 $CMTFile.$WLIP2 > $CMTFile
	
	rm $CMTFile.bak
	if [ $? -ne 0 ]
	then
		echo "ɾ���ļ�[$CMTFile.bak]ʧ��"
	fi
}

#main
echo "����һ�����Ŀ�ʼ..."
CreateDirectory $LocalPath

echo "��ȡ����[$WLIP1]�ϵ�һ������..."
GetCMTFileFromWL $WLIP1 $WLUser1 $WLPort

echo "��ȡ����[$WLIP2]�ϵ�һ������..."
GetCMTFileFromWL $WLIP2 $WUser2 $WPort

echo "�ϲ�һ������..."
CombineCMTFile

echo "����һ�����Ľ���..."

exit 0