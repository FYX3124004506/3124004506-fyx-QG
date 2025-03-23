#include<stdio.h>
#include<stdlib.h>

//������нڵ�ṹ��
typedef struct QueueNode {
	//���巺�����ݣ���void*��������ָ�룩
	void* data;
	//����ڵ�
	struct QueueNode* next;
}QueueNode;

//�������
typedef struct {
	QueueNode* front;//��ͷ
	QueueNode* tail;//��β
}Queue;

//��ʼ������
void initQueue(Queue* q) {
	q->front = q->tail = NULL;
}

//���
void inQueue(Queue* q, int num) {
	//Ϊ�½ڵ�����ڴ�
	QueueNode* newNode =(QueueNode*)malloc(sizeof(QueueNode));
	//Ϊ���ݷ����ڴ�
	int* data = (int*)malloc(sizeof(int));
	if (newNode == NULL) {
		printf("�ڴ����ʧ��\n");
		return;
	} if (data == NULL) {
		printf("�ڴ����ʧ��\n");
		return;
	}
	//��ֵ
	*data = num;
	newNode->data = data;//!!!
	newNode->next = NULL;
	
	if (q->tail == NULL) {
		//�������Ϊ��,��ͷ��βָ���½ڵ�
		q->front = q->tail = newNode;
	}
	else {
		//�����Ϊ�գ���β�����½ڵ�
		q->tail->next = newNode;
		//���¶�β
		q->tail = newNode;
	}
}

//����
void* outQueue(Queue* q) {
	//���Ϊ��
	if (q->front == NULL) return NULL;
	QueueNode* temp = q->front;
	void* data = temp->data;//��סͷ�ڵ��ֵ
	//������ͷ
	q->front = q->front->next;
	//������º�Ϊ��˵���Ѿ��޽ڵ�,ͬʱ����β��Ϊ�գ���ֹ����ָ��
	if (q->front == NULL)q->tail = NULL;
	free(temp);
	return data;
}

int main() {
	Queue q;//�������
	initQueue(&q);//��ʼ������
	int demand, num;
	void* data;
	while (1) {
		printf("1�����\n2������\n3���˳�\n");
		scanf_s("%d", &demand);
		switch (demand) {
		case 1:
			//���
			printf("�������������(����)��");
			scanf_s("%d", &num);
			inQueue(&q, num);
			break;
		case 2:
			//����
			data = outQueue(&q);
			if (data == NULL)//���Ϊ��
			{ printf("����Ϊ�գ��޷�����!"); }
			else {
				printf("����Ԫ�أ�%d\n", *(int*)data);
				free(data);
			}
			break;
		case 3:
			return 0;
		default:
			printf("�����������������\n");
			break;
		}
	}
	return 0;
}