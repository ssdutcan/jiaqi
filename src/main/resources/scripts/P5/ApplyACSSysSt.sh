function p713
{
clear
printf "\n\n\t"
#printf "������������(yyyymmdd)"
#read clear_date
clear_date=`date +'%Y%m%d'`
while true
do
	if [ ${#clear_date} != 8 ]
	then
		printf "\t�������ڸ�ʽ������ȷ��ʽ(yyyymmdd)\n"
		printf "\t������������(yyyymmdd):"
		#read clear_date
	else
		#printf "\t�Ƿ�ȷ�����������($clear_date)ϵͳ״̬(y/n): "
		#read choice
		#if [ x$choice = "xy" -o x$choice = "xY" ]
		#then
	break
		#elif [ x$choice = "xn" -o x$choice = "xN" ]
		#then
		# return 0
		# fi
	fi
done

sqlplus -s $STDB_CONN <<EOF
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
delete from RECPT_SMY_VRF_APLY_RGS where ACS_RCNCL_DT=to_date($clear_date,'yyyymmdd');
insert into RECPT_SMY_VRF_APLY_RGS (ACS_MSGIDNO, ACS_ITT_PCP_INST, ACS_RCNCL_DT)
values (concat('9999',to_char(sysdate,'yyyymmddhhMIssss')), 'C1010511003703',to_date($clear_date,'yyyymmdd'));
update MSGRP_PCSG_CTRL_TBL set MSGIDNO = (select ACS_MSGIDNO from RECPT_SMY_VRF_APLY_RGS where ACS_RCNCL_DT=to_date($clear_date,'yyyymmdd'))
where MSGIDNO='9999999999' and MSGRP_TP='AMFE.660.001.01' and CLRG_DT=to_date($clear_date,'yyyymmdd');
commit;
EOF
if [ $? -eq 0 ]
then
	printf "\tϵͳ״̬���뱨�ĵǼ����\n"
else
	printf "\tϵͳ״̬���뱨�ĵǼ�ʧ��\n"
fi
printf "\t�����������"
read xxx
return 0
}

p713