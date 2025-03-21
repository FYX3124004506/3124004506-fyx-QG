#include<stdio.h>
#include <stdlib.h>

//ʵ�ֵ�������ADT
//����һ����������ڵ�
typedef struct ListNode
{	int n;
	struct Listnode* next;
}ListNode;

//�����µĵ�������ڵ�
ListNode* createNode(int n) {
	ListNode* newNode = (ListNode*)malloc(sizeof(ListNode));//Ϊ�½ڵ㶯̬�����ڴ棬ʵ�ֽڵ�ĸ���
	// ����ڴ�����Ƿ�ɹ�
	if (newNode == NULL) {
		fprintf(stderr, "�ڴ����ʧ��\n");
		return NULL;
	}
	newNode->n=n;
	newNode->next = NULL;
	return newNode;
}

//β������ڵ�
void addNodeTail(ListNode** head, int n) {
	ListNode* newNode = createNode(n);//���������½ڵ㽻����ָ��
	if (*head == NULL) {//��Ϊͷָ��
		*head = newNode;
	}
	else {//����Ϊͷָ��
		ListNode* current = *head;//������ָ�벢ʹ��ָ��ָ������ͷ�ڵ�
		while (current->next != NULL)//ֻҪָ�����һ���ڵ㲻Ϊ�����������ֱ��Ϊ����ֹͣ
		{
			current = current->next;
		}
		current->next = newNode;//����������ָ��������β����������
	}
}

//������������
void checkList(ListNode* head) {
	ListNode* current = head;
	while (current != NULL) {
		printf("%d ->", current->n);
		current = current->next;
	}
	printf("NULL\n");
}

//ʵ��˫������ADT
//����һ��˫������ڵ�
typedef struct DoubleListNode
{
	int n;
	struct Listnode* pre;//ǰ��ָ��
	struct Listnode* next;
}DoubleListNode;

//�����µ�˫������ڵ�
DoubleListNode* createDoubleNode(int n) {
	DoubleListNode* newNode = (DoubleListNode*)malloc(sizeof(DoubleListNode));//Ϊ�½ڵ㶯̬�����ڴ棬ʵ�ֽڵ�ĸ���
	// ����ڴ�����Ƿ�ɹ�
	if (newNode == NULL) {
		fprintf(stderr, "�ڴ����ʧ��\n");
		return NULL;
	}
	newNode->n = n;
	newNode->pre = NULL;
	newNode->next = NULL;
	return newNode;
}
 
//ͷ������ڵ�
void addDoubleNodeHead(DoubleListNode** head, int n) {
	DoubleListNode* newNode = createDoubleNode(n);
	newNode->next = *head;
	if (*head != NULL) {//ͷ�ڵ㲻Ϊ����ǰָ��ָ���½ڵ�
		(*head)->pre = newNode;
	}
	*head = newNode;
}

//β������ڵ�
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
		current->next = newNode;//ͬ��
		newNode->pre = current;
	}
}

//����˫������
void checkDoubleList(DoubleListNode* head) {
	DoubleListNode* current = head;
	while (current != NULL) {
		printf("%d -> ", current->n);
		current = current->next;
	}
	printf("NULL\n");
}

//�ҵ���������е�
ListNode* findMiddleList(ListNode* head) {
	if (head == NULL) {
		return head;
	}
	else {
		//��������ָ��,����ָ�뵽�յ��ʱ����ָ��λ�ü�Ϊ�е�λ��
		ListNode* slow = head;
		ListNode* fast = head;
		while (fast != NULL && fast->next != NULL) {
			slow = slow->next;
			fast = fast->next;
			fast = fast->next;//��ָ���ƶ�����
		}
		return slow;
	}
}

//��������ż����
ListNode* OddEvenList(ListNode* head) {
	if (head == NULL)return head;
	ListNode* odd = head;
	ListNode* even = head->next;
	ListNode* evenhead =even;
	while (even != NULL && even->next != NULL) {
		//��1��2��3��4
		odd->next = even->next;//2->3
		odd = odd->next;//1->3
		even->next = odd->next;//3->4
		even = even->next;//2->4
	}
	odd->next = evenhead;
	return head;
}

//�ж������Ƿ�ɻ�
int checkCycle(ListNode* head) {
	if (head == NULL)return 0;//�������޻�
	ListNode* slow = head;
	ListNode* fast = head;
	while (fast != NULL && fast->next != NULL) {
		slow = slow->next;
		fast = fast->next;
		fast = fast->next;//��ָ��ƫ������
		if (fast == slow) {//����ָ�����������л�
			return 1;
		}
	}return 0;//�޻�

}

//��ת�����ǵݹ飩
ListNode* reversListNOGUI(ListNode* head) {
	ListNode* prev = NULL;
	ListNode* current = head;
	ListNode* next = NULL;

	while (current != NULL) {
		next = current->next; // ������һ���ڵ�
		current->next = prev; // ��ת��ǰ�ڵ��ָ��
		prev = current; // �ƶ� prev ָ��
		current = next; // �ƶ� current ָ��
	}

	return prev; // �����µ�ͷ�ڵ�
}


//��ת�����ݹ飩(����ai�����ǲ�̫���ݹ�����̣�ģ���ľ�ȷ)
//�����������з���������������һ���ֵ��ֱ������ĳ��ֵ�ٵݹ����
ListNode* reversListGUI(ListNode* head) {
	if (head == NULL || head->next == NULL)return head;//���ݹ鵽�գ��򷵻�
	ListNode* newhead = reversListGUI(head->next);//��Ϊ����ǰ����һ��
	//ָ�����
	ListNode* newNode = head->next;
	newNode->next = head;//����ǰ�ڵ�ĺ�һ���ڵ�ָ���Լ�
	head->next = NULL;//ȷ��ԭ����ͷ�ڵ��Ϊβ�ڵ�ָ��NULL
	return newhead;
}

int main() {
	ListNode* head = NULL;
	addNodeTail(&head, 1);
	addNodeTail(&head, 2);
	addNodeTail(&head, 3);
	addNodeTail(&head, 4);
	addNodeTail(&head, 5);

	printf("������");
	checkList(head);

	DoubleListNode* doublehead = NULL;
	addDoubleNodeTail(&doublehead, 1);
	addDoubleNodeTail(&doublehead, 2);
	addDoubleNodeTail(&doublehead, 3);
	addDoubleNodeTail(&doublehead, 4);

	printf("˫������:");
	checkDoubleList(doublehead);

	ListNode* middle = findMiddleList(head);
	printf("�������е�ֵ:%d\n", middle->n);

	int cycle = checkCycle(head);
	printf("�����Ƿ��л�:%d\n", cycle);

	ListNode* reversehead = reversListGUI(head);
	printf("��ת�������:\n");
	checkList(reversehead);
	return 0;
}