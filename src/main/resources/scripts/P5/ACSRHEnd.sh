#!/bin/sh
m_choice=0
ACSName=0
ACSDate=0
ACSStatus=0

QuerySettleStatusMenu()
{
	clear
	echo "ϵͳ��ǰʱ��: $showDate $showTime"
	echo ""
	echo -e "	ϵͳ����\tϵͳ���\t��������\tϵͳ״̬\t��ǰ����"
	echo "		------------------------------------------"
	echo -e "	ACS\t\t${ACSName}\t\t${ACSDate}\t${ACSShowStatus}\t\t${ACSStatus}"
	echo "		------------------------------------------"
}

GetRouteShowStatus()
{
	if [ $ACSStatus = "�ռ�" ]
	then
		ACSShowStatus="�ռ�"
		ACSStatus=""
	else
		ACSShowStatus="����"
	fi
}

QuerySettleStatus()
{
sys_id=$1
returnValue=`sqlplus -s $STDB_CONN<<! 2>/dev/null
set head off
select STM_ID, to_char(CLRG_DT,'yyyy-mm-dd'),decode(DYTM_EOD_STCD,'0','�ռ�','1','���տ�ʼ','2','���ܶ���','3','���ܲ���',DYTM_EOD_STCD,'�쳣״̬') from EOD_STM_ST_TBL where STM_ID='$sys_id';
!`
	
	ACSName=`echo $returnValue|awk '{print $1}'`
	ACSDate=`echo $returnValue|awk '{print $2}'`
	ACSStatus=`echo $returnValue|awk '{print $3}'`
	
	GetRouteShowStatus
	showDate=`date +%-Y-%m-%d`
	showTime=`date +%H:%M:%S`
	QuerySettleStatusMenu
	
	echo -e "\n���س�����...	\c"
	read inputValue
}

menu()
{
	clear
	echo
	echo
	#printf "\t\t*****************************************************\n"
	printf "\t\t|******************* ACS���� **************************|\n"
	printf "\t\t|****************************************************|\n"
	printf "\t\t|*				1 ����״̬��ѯ							*|\n"
	printf "\t\t|*				2 ��������								*|\n"
	printf "\t\t|*				3 �������л�������							*|\n"
	printf "\t\t|*				4 ����ϵͳ״̬							*|\n"
	printf "\t\t|*				q �˳�								*|\n"
	printf "\t\t|****************************************************|\n"
	tput sgr 0 1;printf "\t\t\t����������ѡ�����: ";tput sgr 0 0;
	read m_choice;
}

main()
{
	while true
	do
		menu
		if [ "x${m_choice}" = "xq" -o "x${m_choice}" = "xQ" ]
		then
			break
		fi
		case $m_choice in
		1)
			QuerySettleStatus "ACS"
		;;
		2)
			RerunACSTask.sh
		;;
		3)
			ApplyACSTotalData.sh "ACS"
		;;
		4)
			ApplyACSSysst.sh
		*)
			continue
		;;
		esac
	done
}

main