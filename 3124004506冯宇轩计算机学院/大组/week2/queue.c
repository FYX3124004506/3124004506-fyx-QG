#include<stdio.h>
#include<stdlib.h>

//定义队列节点结构体
typedef struct QueueNode {
	//定义泛型数据，用void*（无类型指针）
	void* data;
	//链表节点
	struct QueueNode* next;
}QueueNode;

//定义队列
typedef struct {
	QueueNode* front;//队头
	QueueNode* tail;//队尾
}Queue;

//初始化队列
void initQueue(Queue* q) {
	q->front = q->tail = NULL;
}

//入队
void inQueue(Queue* q, int num) {
	//为新节点分配内存
	QueueNode* newNode =(QueueNode*)malloc(sizeof(QueueNode));
	//为数据分配内存
	int* data = (int*)malloc(sizeof(int));
	if (newNode == NULL) {
		printf("内存分配失败\n");
		return;
	} if (data == NULL) {
		printf("内存分配失败\n");
		return;
	}
	//赋值
	*data = num;
	newNode->data = data;//!!!
	newNode->next = NULL;
	
	if (q->tail == NULL) {
		//如果队列为空,队头队尾指向新节点
		q->front = q->tail = newNode;
	}
	else {
		//如果不为空，队尾连接新节点
		q->tail->next = newNode;
		//更新队尾
		q->tail = newNode;
	}
}

//出队
void* outQueue(Queue* q) {
	//如果为空
	if (q->front == NULL) return NULL;
	QueueNode* temp = q->front;
	void* data = temp->data;//记住头节点的值
	//调整队头
	q->front = q->front->next;
	//如果更新后为空说明已经无节点,同时将队尾设为空，防止悬空指针
	if (q->front == NULL)q->tail = NULL;
	free(temp);
	return data;
}

int main() {
	Queue q;//定义队列
	initQueue(&q);//初始化队列
	int demand, num;
	void* data;
	while (1) {
		printf("1、入队\n2、出队\n3、退出\n");
		scanf_s("%d", &demand);
		switch (demand) {
		case 1:
			//入队
			printf("请输入入队数据(整数)：");
			scanf_s("%d", &num);
			inQueue(&q, num);
			break;
		case 2:
			//出队
			data = outQueue(&q);
			if (data == NULL)//如果为空
			{ printf("队列为空，无法出队!"); }
			else {
				printf("出队元素：%d\n", *(int*)data);
				free(data);
			}
			break;
		case 3:
			return 0;
		default:
			printf("输入错误，请重新输入\n");
			break;
		}
	}
	return 0;
}