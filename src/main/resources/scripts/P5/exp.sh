#!/bin/ksh
userid=$ZFDB_CONN
if [ "x$2" = "x" ];then
	userid=$ZFDB_CONN
else
	if [ "x$2" = "xzf" ];then
		userid=$ZFDB
	fi
	if [ "x$2" = "xfx" ];then
		userid=$FXDB_CONN
	fi
	if [ "x$2" = "xst" ];then
		userid=$STDB_CONN
	fi
fi

if [ "x$1" = "x" ];then
	echo " ��̨:��ĳ������dump�ļ�"
	echo " USG:$0 tbname!"
	exit 1
fi
echo "����${1}��ʼ..."
exp $userid file=$1.dmp log=$1.log tables=$1 buffer=4096000 feedback=10000
if [ $? -eq 0 ];then
	echo "����${1}�ɹ��������${1}.dmp�У�"
	exit 0
else
	echo "����${1}ʧ�ܣ���鿴��־${1}.log!"
	exit 1
fi