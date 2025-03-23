#include<stdio.h>
#include<stdlib.h>

//定义链栈节点
typedef struct StackNode {
	double data;//节点的数据
	struct StackNode* next;
}StackNode;

//定义链栈结构
typedef struct {
	StackNode* top;//指向栈的顶节点的指针
}LinkStack;

//初始化链栈
void initLinkStack(LinkStack* s) {
	s->top = NULL;//将栈顶指针设为NULL，表示空栈
}

//判断链栈是否为空
int isempty(LinkStack* s) {
	return s->top == NULL;//若为空则返回1，否则返回0
}

//入栈
void push(LinkStack* s, double newdata) {
	//创建新节点并分配内存
	StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
	//将数据存入新节点
	newNode->data = newdata;
	//新节点的next指向栈顶
	newNode->next = s->top;
	//将栈顶更新为新节点
	s->top = newNode;
}

//出栈
//移除栈顶节点并返回节点数据
double pop(LinkStack* s) {
	 //检查栈是否为空，为空则终止运行
	if (isempty(s)) {
		printf("栈为空，无法出栈!");
		exit(1);//终止程序
	}

	//获取栈顶的节点数据
	double topdata=s->top->data;
	//保存栈顶的指针
	StackNode* keep = s->top;
	//更新栈顶指针为原栈顶的下一个节点（即为NULL）
	s->top = s->top->next;
	//释放原栈顶节点的内存
	free(keep);
	return topdata;
}

//获取栈顶元素的数据，不删除栈顶值
double getTop(LinkStack* s) {
	//若栈为空，终止程序
	if (isempty(s) ){
		printf("栈为空，无法获取元素！");
		exit(1);
	}
	return s->top->data;//返回栈顶元素
}

//运算符优先级
//定义优先级："*""/"高于"+""-"
int OperatorPrecedence(char opt) {
	//通过数字大小判定优先级
	if (opt == '+' || opt == '-')return 1;
	if (opt == '*' || opt == '/')return 2;
	return 0;
}
//执行计算
double Operation(double a, double b, char opt) {
	switch (opt) {
	case '+':
		return a + b;
	case '-':
		return a - b;
	case '*':
		return a * b;
	case '/':
		if (b == 0) {//分母为零则终止运行
			printf("错误：除数不为0");
			exit(1);
		}
		return a / b;
	}
	return 0;
}

//判断字符是否为数字
int isnumber(char c) {
	return c >= '0' && c <= '9';
}

//计算字符串长度
int length(char* str) {
	int len = 0;
	while (str[len] != '\0') {
		len++;
	}
	return len;
}

//计算表达式的值
double evaluate(char* expression) {
	LinkStack stack,opts;//定义两个链栈，
	initLinkStack(&stack);//初始化链栈(存储操作数)
	initLinkStack(&opts);//初始化链栈（存储运算符）
	//char lastopt = '\0';//初始化上一个遇到的运算符

	//遍历表达式的所有字符
	for (int i=0; i < length(expression); i++) {
		//跳过空字符
		if (expression[i] == ' ') {
			continue;
		}
		//如果当前字符为数字
		if (isnumber(expression[i])) {
			//存储当前的数字
			double num = 0;
			// 当前字符及其后续字符都是数字时，将它们组合成一个完整的数字
			while (i < length(expression) && isnumber(expression[i])) {
				// 通过乘以 10 并加上当前字符对应的数字，将字符转换为数值
				num = (num * 10) + (expression[i] - '0');
				// 移动到下一个字符
				i++;
			}
			i--;
			push(&stack, num);
		}
		//遇到左括号入栈
		else if (expression[i] == '(') {
			push((&opts), (double)expression[i]);
		}
		//遇到右括号
		else if (expression[i] == ')') {
			//// 当运算符栈 ops 不为空，
			// 并且栈顶元素不是左括号 '(' 时
			while (!isempty(&opts) && (char)getTop(&opts) != '(') {
				double num2 = pop(&stack);
				double num1 = pop(&stack);
				char opt = (char)pop(&opts);
				//计算结果并入栈
				push(&stack, Operation(num1, num2, opt));
			}
			// 由于栈中存储的是 double 类型
			// 需要将其强制转换为 char 类型
			//前一个运算符不为空且遇到左括号时
			if (!isempty(&opts) && (char)getTop(&opts) == '(') {
				pop(&opts);
			}
		}
		//如果遇到运算符
		else if (expression[i] == '+' || expression[i] == '-' || expression[i] == '*' || expression[i] == '/') {
			// 当运算符栈 ops 不为空，
			// 并且栈顶运算符的优先级大于等于当前运算符的优先级时
			while (!isempty(&opts) && OperatorPrecedence((char)getTop(&opts)) >= OperatorPrecedence(expression[i])) {
				double num2 = pop(&stack);
				double num1 = pop(&stack);
				char opt = (char)pop(&opts);
				push(&stack, Operation(num1, num2, opt));
			}
			push(&opts, (double)expression[i]);
		}
	}
	// 当运算符栈 opts 不为空时，持续进行运算,清理剩余运算符
	while (!isempty(&opts)) {
		double num2 = pop(&stack);
		double num1 = pop(&stack);
		char opt = (char)pop(&opts);
		push(&stack, Operation(num1, num2, opt));
	}

	return getTop(&stack);
}

int main(){
	//定义一个数组用于存储输入的表达式
	char expression[100];
	printf("请输入一个算数表达式:");

	//用于记录字符的位置
	int i = 0;
	//用于记录读取的字符
	char c;

	//逐字符读取用户的输入
	while ((c = getchar())!='\n' && i < 99){
		//读取的字符存入数组
		expression[i] = c;
		i++;
	}
	//末尾添加结束符
	expression[i] = '\0';
	//计算表达式的值
	double result = evaluate(expression);
	printf("计算结果为：%.2f\n", result);
	return 0;
}


