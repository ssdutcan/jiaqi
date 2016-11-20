apply919()
{
#set -x
sleep 2
#clear
msgid=`date +'%Y%m%d%H%M%S%S'`
datime=`date +'%Y-%m-%d %H:%M:%S'`
dt=`date +'%Y%m%d'`
olid=`date +'%Y%m%d%H%M%S%H%M%S'`
pdtm=`date +'%Y%m%d%N'`
accno=$1
#echo $msgid
#echo $datime
#echo $dt
#echo $olid
#echo $pdtm
#echo $accno
#echo $msgid >>apply919_msgid.txt
echo "`date +'%Y%m%d%H%M%S'`:���ı�ʶ��[$msgid]��������������[$accno]"
echo "`date +'%Y%m%d%H%M%S'`:���ı�ʶ��[$msgid]��������������[$accno]" >>apply919_msgid.txt
#read -e yn
#if [ "x$yn" != "xy" -a "x$yn" != "xY" ]
#then
#	echo "�ѷ�������"
#	exit 0
#fi

sqlplus -s $ZFDB_CONN <<EOF
set linesize 1000
set pagesize 0
set heading off
set term off
set termout off
set feedback off
set newpage none
set echo off
set heading off
set trim off

insert into BASE_DATA_MGT_DTL_RGSTBOOK
(ITT_PCPR_BRNO,MSGRP_RCV_SND_IDCD,MSGIDNO,MSGRP_DTL_SN,BLNG_DRC_PCPR_BRNO,MULTI_TENANCY_ID,PYST_DTBS_TM)
values (
'105100000017','02',$msgid,1,$accno,'CN000',$pdtm
);

insert into BASE_DATA_MGT_MSGRP_RGSTBOOK
values (
'$dt',
'105100000017',
'02',
$msgid,
'ccms.919.001.01',
'','','','','',
to_date($dt,'yyyymmdd'),
0,
'CN000'
);
EOF
}

cat apply919_msgid.txt >>apply919_msgid.txt.bak
cat /dev/null >apply919_msgid.txt
clear
#echo "������Ҫ����Ĳ�������ţ�����������ÿո�ֿ���'
#read accnos
if [ -f ${HOME}/log/cnaps2_tux_svr/sign.log ]
then
accnos_cnaps=`cd ${HOME}/log/cnaps2_tux_svr;grep ʧ�� sign.log | grep ȡ�����й�Կʧ�� |awk -F '[' '{print $2}' | awk -F ']' '{print $1}' | awk '{print substr($0,1,length($0)-3)} | sort -u'`
fi

echo "��Ҫ����Ķ���ϵͳ����֤�������Ϊ��"
echo "$accnos_cnaps"
echo "�Ƿ�ȷ������?(y/n)"
read -e yn
if [ "x$yn" = "xy" -o "x$yn" = "xY" ]
then
	for acc in $accnos_cnaps
	do
		apply919 $acc
		#echo "apply cnaps"
	done
else
	echo "�ѷ�������"
fi

if [ -f ${HOME}/log/ibps_tux_svr/sign.log ]
then
accnos_ibps=`cd ${HOME}/log/ibps_tux_svr;grep ʧ�� sign.log | grep ȡ�����й�Կʧ�� |awk -F '[' '{print $2}' | awk -F ']' '{print $1}' | awk '{print substr($0,1,length($0)-3)} | sort -u'`
fi

echo "��Ҫ����ĳ���ϵͳ����֤�������Ϊ��"
echo "$accnos_ibps"
echo "�Ƿ�ȷ������?(y/n)"
read -e yn
if [ "x$yn" = "xy" -o "x$yn" = "xY" ]
then
	for acc in $accnos_ibps
	do
		apply919 $acc
		#echo "apply ibps"
	done
else
	echo "�ѷ�������"
fi

echo "�ɹ����"
}