#----cr by ������ 20130913
#----���ܣ���ĳ�����Ӧ��dump�ļ����뵽����
userid=$ZFDB_CONN
if [ "x$2" = "x" ];then
	userid=$ZFDB_CONN
else
	if [ "x$2" = "xzf" ];then
		userid=$ZFDB_CONN
	fi
	if [ "x$2" = "xfx" ];then
		userid=$FXDB_CONN
	fi
	if [ "x$2" = "xst" ];then
		userid=$STDB_CONN
	fi
fi

if [ "x$1" = "x" ];then
	echo " ��̨:��ĳ�����Ӧ��dmp�ļ����뵽����"
	echo " USG:$0 tbname!"
	exit
fi
echo $1
imp $userid file=$1.dmp log=$1.log tables=$1 buffer=2048000 commit=y ignore=y feedback=10000
if [ $? -eq 0 ];then
	echo "����${1}.dmp�ɹ���"
	exit 0
else
	exit "����${1}.dmpʧ�ܣ���鿴��־${1}.log!"
	exit 1
fi