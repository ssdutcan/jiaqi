#!/bin/bash

#����ֵ���û�пո�����Ҫ˫���ţ���������˫����
URL=http://128.192.124.41:8101/npmsrmb/command/

echo -e "��ѡ�������ű���\n"
echo "1. ����ű�"
echo "2. ɨ��ű�"

while true
do
	printf ">>"
	read type	#ʹ��read�������ʱ��ü�˫���ţ���Ϊ������ַ��������пո�
	
	#��read�������һ���жϣ�
	#1.�Ƿ�Ϊ��    2.�Ƿ�Ϊexit��quit  3. ����ȡֵ    4. ����
	if [ "$type" = "" ];then
		continue
	elif [ "$type" = "exit" ] || [ "$type" = "quit" ];then
		exit 1	#ִ�нű�ʱҪ��./ �� sh�������� . ִ�У�������˳��ն�
	elif [ "$type" = "1" ];then
		curl --data "type=pcache command='help'" $URL
		break
	elif [ "$type" = "2" ];then
		curl --data "type=scan oper='help'" $URL
		break
	else
		echo -e ">>������ѡ��"
	fi
done

while true
do
	printf ">>"
	read command
	if [ "$command" = "" ];then
		continue
	elif [ "$command" = "exit" ] || [ "$command" = "quit" ];then
		exit 2
	else
		if [ "$type" = "1" ];then
			curl --data "type=pcache command='$command'" $URL
		else
			curl --data "type=scan oper='$command'" $URL
		fi
	fi
done