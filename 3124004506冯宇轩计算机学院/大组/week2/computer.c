#include<stdio.h>
#include<stdlib.h>

//������ջ�ڵ�
typedef struct StackNode {
	double data;//�ڵ������
	struct StackNode* next;
}StackNode;

//������ջ�ṹ
typedef struct {
	StackNode* top;//ָ��ջ�Ķ��ڵ��ָ��
}LinkStack;

//��ʼ����ջ
void initLinkStack(LinkStack* s) {
	s->top = NULL;//��ջ��ָ����ΪNULL����ʾ��ջ
}

//�ж���ջ�Ƿ�Ϊ��
int isempty(LinkStack* s) {
	return s->top == NULL;//��Ϊ���򷵻�1�����򷵻�0
}

//��ջ
void push(LinkStack* s, double newdata) {
	//�����½ڵ㲢�����ڴ�
	StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
	//�����ݴ����½ڵ�
	newNode->data = newdata;
	//�½ڵ��nextָ��ջ��
	newNode->next = s->top;
	//��ջ������Ϊ�½ڵ�
	s->top = newNode;
}

//��ջ
//�Ƴ�ջ���ڵ㲢���ؽڵ�����
double pop(LinkStack* s) {
	 //���ջ�Ƿ�Ϊ�գ�Ϊ������ֹ����
	if (isempty(s)) {
		printf("ջΪ�գ��޷���ջ!");
		exit(1);//��ֹ����
	}

	//��ȡջ���Ľڵ�����
	double topdata=s->top->data;
	//����ջ����ָ��
	StackNode* keep = s->top;
	//����ջ��ָ��Ϊԭջ������һ���ڵ㣨��ΪNULL��
	s->top = s->top->next;
	//�ͷ�ԭջ���ڵ���ڴ�
	free(keep);
	return topdata;
}

//��ȡջ��Ԫ�ص����ݣ���ɾ��ջ��ֵ
double getTop(LinkStack* s) {
	//��ջΪ�գ���ֹ����
	if (isempty(s) ){
		printf("ջΪ�գ��޷���ȡԪ�أ�");
		exit(1);
	}
	return s->top->data;//����ջ��Ԫ��
}

//��������ȼ�
//�������ȼ���"*""/"����"+""-"
int OperatorPrecedence(char opt) {
	//ͨ�����ִ�С�ж����ȼ�
	if (opt == '+' || opt == '-')return 1;
	if (opt == '*' || opt == '/')return 2;
	return 0;
}
//ִ�м���
double Operation(double a, double b, char opt) {
	switch (opt) {
	case '+':
		return a + b;
	case '-':
		return a - b;
	case '*':
		return a * b;
	case '/':
		if (b == 0) {//��ĸΪ������ֹ����
			printf("���󣺳�����Ϊ0");
			exit(1);
		}
		return a / b;
	}
	return 0;
}

//�ж��ַ��Ƿ�Ϊ����
int isnumber(char c) {
	return c >= '0' && c <= '9';
}

//�����ַ�������
int length(char* str) {
	int len = 0;
	while (str[len] != '\0') {
		len++;
	}
	return len;
}

//������ʽ��ֵ
double evaluate(char* expression) {
	LinkStack stack,opts;//����������ջ��
	initLinkStack(&stack);//��ʼ����ջ(�洢������)
	initLinkStack(&opts);//��ʼ����ջ���洢�������
	//char lastopt = '\0';//��ʼ����һ�������������

	//�������ʽ�������ַ�
	for (int i=0; i < length(expression); i++) {
		//�������ַ�
		if (expression[i] == ' ') {
			continue;
		}
		//�����ǰ�ַ�Ϊ����
		if (isnumber(expression[i])) {
			//�洢��ǰ������
			double num = 0;
			// ��ǰ�ַ���������ַ���������ʱ����������ϳ�һ������������
			while (i < length(expression) && isnumber(expression[i])) {
				// ͨ������ 10 �����ϵ�ǰ�ַ���Ӧ�����֣����ַ�ת��Ϊ��ֵ
				num = (num * 10) + (expression[i] - '0');
				// �ƶ�����һ���ַ�
				i++;
			}
			i--;
			push(&stack, num);
		}
		//������������ջ
		else if (expression[i] == '(') {
			push((&opts), (double)expression[i]);
		}
		//����������
		else if (expression[i] == ')') {
			//// �������ջ ops ��Ϊ�գ�
			// ����ջ��Ԫ�ز��������� '(' ʱ
			while (!isempty(&opts) && (char)getTop(&opts) != '(') {
				double num2 = pop(&stack);
				double num1 = pop(&stack);
				char opt = (char)pop(&opts);
				//����������ջ
				push(&stack, Operation(num1, num2, opt));
			}
			// ����ջ�д洢���� double ����
			// ��Ҫ����ǿ��ת��Ϊ char ����
			//ǰһ���������Ϊ��������������ʱ
			if (!isempty(&opts) && (char)getTop(&opts) == '(') {
				pop(&opts);
			}
		}
		//������������
		else if (expression[i] == '+' || expression[i] == '-' || expression[i] == '*' || expression[i] == '/') {
			// �������ջ ops ��Ϊ�գ�
			// ����ջ������������ȼ����ڵ��ڵ�ǰ����������ȼ�ʱ
			while (!isempty(&opts) && OperatorPrecedence((char)getTop(&opts)) >= OperatorPrecedence(expression[i])) {
				double num2 = pop(&stack);
				double num1 = pop(&stack);
				char opt = (char)pop(&opts);
				push(&stack, Operation(num1, num2, opt));
			}
			push(&opts, (double)expression[i]);
		}
	}
	// �������ջ opts ��Ϊ��ʱ��������������,����ʣ�������
	while (!isempty(&opts)) {
		double num2 = pop(&stack);
		double num1 = pop(&stack);
		char opt = (char)pop(&opts);
		push(&stack, Operation(num1, num2, opt));
	}

	return getTop(&stack);
}

int main(){
	//����һ���������ڴ洢����ı��ʽ
	char expression[100];
	printf("������һ���������ʽ:");

	//���ڼ�¼�ַ���λ��
	int i = 0;
	//���ڼ�¼��ȡ���ַ�
	char c;

	//���ַ���ȡ�û�������
	while ((c = getchar())!='\n' && i < 99){
		//��ȡ���ַ���������
		expression[i] = c;
		i++;
	}
	//ĩβ��ӽ�����
	expression[i] = '\0';
	//������ʽ��ֵ
	double result = evaluate(expression);
	printf("������Ϊ��%.2f\n", result);
	return 0;
}


