#!/bin/bash
#################################################
# ProgramName	: taskcheck.sh					#
#												#
# Version		: v1.0							#
#												#
# Description	: �����޸��Զ�����δ�յ�990����ʱ�Ǽǲ�״̬	#
#												#
# Author		: �߽�							#
#################################################

LOOPTIME=60

TASKFILE=$HOME/scripts/taskcheck.txt

SQL_EXEC()
{
sqlplus -S ${connstr}<<!
set head off
set serveroutput off
set trimspool on
set echo off
set pagesize 0
set feedback off
set term off
${sqlcomm}
quit
!
}

connstr=${ZFDB_CONN}

while [ 1 ]
do
	grep "*" $TASKFILE|awk -F '*' '{print $2}'|while read LINE
	do
		#��ȡ������������
		tsknm=`echo $LINE|awk '{print $1}'`
		tblnm=`echo $LINE|awk '{print $3}'`
		
		#�������ͣ����С����������Ŀ��Ʊ����⴦��
		if [ $tsknm == "P5033D003" -o $tsknm == "P5033D007" -o $tsknm == "P5033D011" ]
		then
			#��ȡ��������
			msgtp=`echo $LINE|awk '{print $4}|'|tr -d " "`
			
			#��ȡ��ǰʱ���֡���
			lt_h=`date "+%H"`
			lt_m=`date "+%M"`
			lt_s=`date "+%S"`
			
			#��ȡС������������Ʊ�ʱ����(����Ϊ��λ)
			sqlcomm="select TM_ITRV from MICR_NSTACCT_PKG_CTRL_TBL where MSGRP_TP='$msgtp';"
			itval=`SQL_EXEC`
			if [ $lt_m == "00" ]
			then
				bf_h=`expr "$lt_h - 1"`
				bf_m="59"
				bf_s=$lt_s
			else
				bf_h=$lt_h
				bf_m=`expr $lt_m - $itval`
				bf_s=$lt_s
			fi
			
			bftime=${bf_h}${bf_m}${bf_s}
			
			#��������Ͳ�����δ�յ�990��ִ��ֱ����Ϊʧ��
			sqlcomm="update $tblnm set MSGRP_PCSST='05' where MSGRP_RCV_SND_IDCD='02' and MSGRP_PCSST='09' and SND_CNT>=3 and MSGRP_SND_TM < $bftime;"
			SQL_EXEC
		else
			#��������ʹ�����Ϊ�յ�990��ִ��ֱ����Ϊʧ��
			sqlcomm="update $tblnm set MSGRP_PCSST='05' where MSGRP_RCV_SND_IDCD='02' and MSGRP_PCSST='09' and SND_CNT>=3;"
			SQL_EXEC
		fi
		
		#δ��������ʹ�����δ�յ�990��ִ����Ϊ���������·�
		sqlcomm="update $tblnm set MSGRP_PCSST='01' where MSGRP_RCV_SND_IDCD='02' and MSGRP_PCSST='09' and SND_CNT<3;"
		SQL_EXEC
	done
	#break
	
	sleep ${LOOPTIME}
	
	continue
done