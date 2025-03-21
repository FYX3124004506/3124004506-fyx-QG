#include<stdio.h>
#include <stdlib.h>

//实现单向链表ADT
//定义一个单向链表节点
typedef struct ListNode
{	int n;
	struct Listnode* next;
}ListNode;

//创建新的单项链表节点
ListNode* createNode(int n) {
	ListNode* newNode = (ListNode*)malloc(sizeof(ListNode));//为新节点动态分配内存，实现节点的复制
	// 检查内存分配是否成功
	if (newNode == NULL) {
		fprintf(stderr, "内存分配失败\n");
		return NULL;
	}
	newNode->n=n;
	newNode->next = NULL;
	return newNode;
}

//尾部插入节点
void addNodeTail(ListNode** head, int n) {
	ListNode* newNode = createNode(n);//将创建的新节点交给新指针
	if (*head == NULL) {//若为头指针
		*head = newNode;
	}
	else {//若不为头指针
		ListNode* current = *head;//创建新指针并使新指针指向链表头节点
		while (current->next != NULL)//只要指针的下一个节点不为空则继续遍历直到为空则停止
		{
			current = current->next;
		}
		current->next = newNode;//将创建的新指针与链表尾部连接起来
	}
}

//遍历单项链表
void checkList(ListNode* head) {
	ListNode* current = head;
	while (current != NULL) {
		printf("%d ->", current->n);
		current = current->next;
	}
	printf("NULL\n");
}

//实现双向链表ADT
//定义一个双向链表节点
typedef struct DoubleListNode
{
	int n;
	struct Listnode* pre;//前后指针
	struct Listnode* next;
}DoubleListNode;

//创建新的双向链表节点
DoubleListNode* createDoubleNode(int n) {
	DoubleListNode* newNode = (DoubleListNode*)malloc(sizeof(DoubleListNode));//为新节点动态分配内存，实现节点的复制
	// 检查内存分配是否成功
	if (newNode == NULL) {
		fprintf(stderr, "内存分配失败\n");
		return NULL;
	}
	newNode->n = n;
	newNode->pre = NULL;
	newNode->next = NULL;
	return newNode;
}
 
//头部插入节点
void addDoubleNodeHead(DoubleListNode** head, int n) {
	DoubleListNode* newNode = createDoubleNode(n);
	newNode->next = *head;
	if (*head != NULL) {//头节点不为空则将前指针指向新节点
		(*head)->pre = newNode;
	}
	*head = newNode;
}

//尾部插入节点
void addDoubleNodeTail(DoubleListNode** head, int n) {
	DoubleListNode* newNode = createDoubleNode(n);
	if (*head == NULL) {
		*head = newNode;
	}
	else {
		DoubleListNode* current = *head;
		while (current->next != NULL) {
			current = current->next;
		}
		current->next = newNode;//同理
		newNode->pre = current;
	}
}

//遍历双向链表
void checkDoubleList(DoubleListNode* head) {
	DoubleListNode* current = head;
	while (current != NULL) {
		printf("%d -> ", current->n);
		current = current->next;
	}
	printf("NULL\n");
}

//找到单链表的中点
ListNode* findMiddleList(ListNode* head) {
	if (head == NULL) {
		return head;
	}
	else {
		//创建快慢指针,当快指针到终点点时，慢指针位置即为中点位置
		ListNode* slow = head;
		ListNode* fast = head;
		while (fast != NULL && fast->next != NULL) {
			slow = slow->next;
			fast = fast->next;
			fast = fast->next;//快指针移动两次
		}
		return slow;
	}
}

//单链表奇偶调换
ListNode* OddEvenList(ListNode* head) {
	if (head == NULL)return head;
	ListNode* odd = head;
	ListNode* even = head->next;
	ListNode* evenhead =even;
	while (even != NULL && even->next != NULL) {
		//如1，2，3，4
		odd->next = even->next;//2->3
		odd = odd->next;//1->3
		even->next = odd->next;//3->4
		even = even->next;//2->4
	}
	odd->next = evenhead;
	return head;
}

//判断链表是否成环
int checkCycle(ListNode* head) {
	if (head == NULL)return 0;//空链表，无环
	ListNode* slow = head;
	ListNode* fast = head;
	while (fast != NULL && fast->next != NULL) {
		slow = slow->next;
		fast = fast->next;
		fast = fast->next;//快指针偏移两次
		if (fast == slow) {//快慢指针相遇，则有环
			return 1;
		}
	}return 0;//无环

}

//反转链表（非递归）
ListNode* reversListNOGUI(ListNode* head) {
	ListNode* prev = NULL;
	ListNode* current = head;
	ListNode* next = NULL;

	while (current != NULL) {
		next = current->next; // 保存下一个节点
		current->next = prev; // 反转当前节点的指针
		prev = current; // 移动 prev 指针
		current = next; // 移动 current 指针
	}

	return prev; // 返回新的头节点
}


//反转链表（递归）(求助ai，还是不太理解递归的流程，模糊的精确)
//类似于向函数中反复带入数字求下一项的值，直到等于某个值再递归回来
ListNode* reversListGUI(ListNode* head) {
	if (head == NULL || head->next == NULL)return head;//若递归到空，则返回
	ListNode* newhead = reversListGUI(head->next);//不为空则前往下一项
	//指针调整
	ListNode* newNode = head->next;
	newNode->next = head;//将当前节点的后一个节点指向自己
	head->next = NULL;//确保原来的头节点变为尾节点指向NULL
	return newhead;
}

int main() {
	ListNode* head = NULL;
	addNodeTail(&head, 1);
	addNodeTail(&head, 2);
	addNodeTail(&head, 3);
	addNodeTail(&head, 4);
	addNodeTail(&head, 5);

	printf("单链表：");
	checkList(head);

	DoubleListNode* doublehead = NULL;
	addDoubleNodeTail(&doublehead, 1);
	addDoubleNodeTail(&doublehead, 2);
	addDoubleNodeTail(&doublehead, 3);
	addDoubleNodeTail(&doublehead, 4);

	printf("双向链表:");
	checkDoubleList(doublehead);

	ListNode* middle = findMiddleList(head);
	printf("单链表中点值:%d\n", middle->n);

	int cycle = checkCycle(head);
	printf("链表是否有环:%d\n", cycle);

	ListNode* reversehead = reversListGUI(head);
	printf("反转后的链表:\n");
	checkList(reversehead);
	return 0;
}