#include<conio.h>
#include<process.h>
#include<stdio.h>
#include<dos.h>
#include<stdlib.h>
#include<fstream.h>
#include<graphics.h>
#define LEFT 75
#define RIGHT 77
#define UP 72
#define DOWN 80
#define ENTER 13

class CAR
{
public:
	int vel, cp, init, op1, op2, op3, level;
	long score;
	long lev_score[5];
	char player[20];
	CAR::CAR()
	{
		level = 1;
		vel = 1;
		cp = 300;
		init = 0;
		op1 = 0;
		op2 = -20;
		op3 = -40;
		score = 0;
		lev_score[0] = 1000; lev_score[1] = 3000; lev_score[2] = 6000; lev_score[3] = 8000; lev_score[4] = 13000;
	}
}car;

int randx(void)
{
	int x = random(56 * 8);
	if (x < 22 * 8)
	{
		x = x + (22 * 8);
	}
	return(x);
}

void right()
{
	if (car.cp < 420)
	{
		car.cp += 10;
	}
	return;
}

void left()
{
	if (car.cp > 180)
	{
		car.cp -= 10;
	}
	return;
}

void score_store()
{
	cleardevice();
	setcolor(4);
	settextstyle(2, 0, 6);
	outtextxy(10, 0, "Your score is :");
	gotoxy(20, 2);
	cout << car.score;
	outtextxy(10, 40, "Enter your name to store :");
	gotoxy(36, 4);
	gets(car.player);
	ofstream score;
	score.open("score.txt", ios::app);
	score << "\n";
	score.write(car.player, 20);
	score << car.score;
	score.close();
}

void disp_score()
{
	nosound();
	cleardevice();
	setcolor(20);
	settextstyle(4, 0, 5);
	outtextxy(150, 20, "SCORE BOARD");
	setfillstyle(9, 2);
	bar(125, 75, 500, 80);
	ifstream score("score.txt");
	char ch;
	int i = 0;
	gotoxy(1, 5);
	setcolor(3);
	cout << endl << endl;
	while (score.get(ch))
	{
		score.seekg(i, ios::beg);
		score.get(ch);
		cout << ch;
		i++;
	}
	getch();
	return;
}

void bgmove()
{
	sound(20);
	if (car.init > 5)
	{
		car.init = 1;
	}
	else
	{
		car.init += car.vel;
	}
	setcolor(RED);
	setfillstyle(SOLID_FILL, RED);
	sector(car.cp + 20, 330, 0, 180, 20, 30);
	setfillstyle(LTBKSLASH_FILL, GREEN);
	bar3d(car.cp, 333, car.cp + 37, 360, 4, 4);
	sector(car.cp + 20, 362, 180, 360, 20, 10);
	for (int i = car.init; i < 70; i += 5)
	{
		setcolor(WHITE);
		setfillstyle(1, WHITE);
		bar3d(16 * 8, i * 8, 20 * 8, (i + 2) * 8, 10, 1);
		bar3d(60 * 8, i * 8, 64 * 8, (i + 2) * 8, 10, 1);
	}
	return;
}

void opp1(int x1)
{
	setcolor(RED);

	if (car.op1 < 600)
	{
		setcolor(8);
		car.op1 += 10;
		setfillstyle(1, 8);
		pieslice(x1 + 15, car.op1 + 15, 0, 360, 20);
		//bar3d(x1,car.op1,x1+30,car.op1+30,2,3);
	}
	else
	{
		car.op1 = 0;
	}
	return;
}

void opp2(int x2)
{
	if (car.op2 < 600)
	{
		car.op2 += 10;
		setfillstyle(1, YELLOW);
		setcolor(YELLOW);
		pieslice(x2 + 15, car.op2 + 15, 0, 360, 20);
		//bar3d(x2,car.op2,x2+30,car.op2+30,2,3);
	}
	else
	{
		car.op2 = 0;
	}
	return;
}

void opp3(int x3)
{
	if (car.op3 < 600)
	{
		car.op3 += 10;
		setfillstyle(1, BLUE);
		setcolor(BLUE);
		//pieslice(x3+15,car.op3+15,0,360,20);
		bar3d(x3, car.op3, x3 + 30, car.op3 + 30, 10, 3);
	}
	else
	{
		car.op3 = 0;
	}
	return;
}

int check(int x, int op, int cp)
{
	int a[31], b[31], c[40], d[50];
	for (int i = 0; i < 30; i++)
	{
		a[i] = x + i;
	}
	for (i = 0; i < 30; i++)
	{
		b[i] = op + i;
	}
	for (i = 0; i < 40; i++)
	{
		c[i] = cp + i;
	}
	for (i = 0; i < 50; i++)
	{
		d[i] = 300 + i;
	}
	for (i = 0; i < 40; i++)
	{
		for (int j = 0; j < 50; j++)
		{
			for (int k = 0; k < 30; k++)
			{
				if (a[k] == c[i] && b[k] == d[j])
					return 1;
			}
		}
	}
	return 0;
}

void logo()
{
	for (int i = 1; i < 320; i++)
	{
		setcolor(i + 2);
		setfillstyle(1, i);
		settextstyle(10, 0, 4);
		outtextxy(160, 100, "CAR SURFER");
		settextstyle(10, 0, 3);
		outtextxy(125, 155, "DRIVE TO THE FINISH");
		bar3d(50, 220, 560, 240, 10, 40);
		setcolor(BLUE);
		settextstyle(1, 0, 3);
		outtextxy(70, 390, "LOADING");
		//setcolor(GREEN);
		//bar(80,420,80+100,420+2);
		setfillstyle(9, BLUE);
		bar3d(190, 400, 190 + i, 420, 10, 10);
		delay(1);
	}
	delay(1000);
	cleardevice();
	return;
}

void loss(void)
{
	nosound();
	setcolor(10);
	settextstyle(2, 0, 9);
	outtextxy(180, 150, "ONE CHANCE LOST");
	delay(1000);
	return;
}

void over(void)
{
	nosound();
	for (int i = 1; i < 35; i++)
	{
		setbkcolor(RED);
		cleardevice();
		delay(10);
		settextstyle(7, 0, 7);
		setcolor(RED);
		setbkcolor(0);
		outtextxy(150, 100, "GAME OVER");
		delay(60);
	}
	setbkcolor(0);
	delay(2000); cleardevice();
	score_store();
	getch();
	return;
}

void score()
{
	nosound();
	car.score = car.score + 11 * car.vel;
	setcolor(5);
	settextstyle(2, 0, 6);
	outtextxy(530, 12, "Score ");
	gotoxy(75, 2);
	cout << car.score;
	return;
}

void win()
{
	nosound();
	cleardevice();
	for (int k = 0; k < 2; k++)
	{
		for (int i = 0; i < 15; i++)
		{
			setcolor(i);
			settextstyle(4, 0, 5);
			outtextxy(200, 100, "Congrats!!");
			setcolor(i + 1);
			outtextxy(150, 200, "You Won The Race");
			delay(150);
		}
		exit(0);
	}
	delay(3000);
	score_store();

	return;
}

void level()
{
	nosound();
	int level = car.level;
	if (car.score > car.lev_score[level - 1])
	{
		if (car.level < 5)
		{
			car.level++;
			if (car.vel < 5)
			{
				car.vel++;
			}
			cleardevice();
			/*settextstyle(2,0,3);
			setcolor(4);
			outtextxy(4,12,"Level");
			gotoxy(5,1);
			cout<<car.level;*/

			setcolor(RED);
			settextstyle(10, 0, 7);
			outtextxy(100, 20, "LEVEL ");
			outtextxy(50, 120, "UPGRADED ");
			delay(1000);
		}
		else
		{
			win();
		}
	}
	settextstyle(2, 0, 6);
	setcolor(5);
	outtextxy(4, 12, "Level");
	gotoxy(8, 2);
	cout << car.level;
	return;
}

void about()
{
	nosound();
	char opt;
	while (!kbhit())
	{
		for (int i = 300; i < 600; i++)
		{
			cleardevice();
			setcolor(9);
			settextstyle(4, 0, 5);
			setfillstyle(9, 12);
			outtextxy(220, 0, "ABOUT");
			bar(220, 60, 370, 62);
			settextstyle(2, 0, 5);
			setcolor(2);
			outtextxy(20, 100, "This project is made as a part of ");
			outtextxy(3, 115, "Computer Graphics Project 2012-2013");
			outtextxy(3, 165, "Project By");
			setcolor(6);
			settextstyle(2, 0, 7);
			outtextxy(600 - i, 600 - i, "PUSHKARAJ JADHAV (D7A-33)");
			setcolor(5);
			settextstyle(2, 0, 7);
			outtextxy(i - 150, 215, "MANILA JESHNANI (D7A-35)");
			setcolor(7);
			settextstyle(2, 0, 7);
			outtextxy(i - 150, i - 150, "SALIL KANETKAR (D7A-38)");
			setfillstyle(1, 4);
			bar3d(180, 380, 450, 460, 4, 6);
			if (kbhit())
				break;
			delay(125);
		}
	}
	getch();
	return;
}

int play(void)
{
	int life = 3, m, x1, x2, x3, s;
re:
	while (life)
	{
		cleardevice();
		if (car.op1 == 0)
		{
			x1 = randx();
			car.op1++;
		}
		else
		{
			opp1(x1);
		}
		if (car.op2 == 0)
		{
			x2 = randx();
			car.op2++;
		}
		else if (car.op2 < 1)
		{
			car.op2++;
		}
		else
		{
			opp2(x2);
		}
		if (car.op3 == 0)
		{
			x3 = randx();
			car.op3++;
		}
		else if (car.op3 < 1)
		{
			car.op3++;
		}
		else
		{
			opp3(x3);
		}
		setcolor(WHITE);
		setfillstyle(0, 0);
		bar3d(0, 0, 80, 70, 4, 3);
		bgmove();
		level();
		score();
		settextstyle(2, 0, 6);
		setcolor(RED);
		outtextxy(4, 44, "Life");
		gotoxy(8, 4);
		cout << life;
		if (check(x2, car.op2, car.cp) || check(x1, car.op1, car.cp) || check(x3, car.op3, car.cp))
		{
			loss();
			life--;

			if (life == 0)
			{
				over();
			}
			car.op1 = 1;
			car.op2 = -16;
			car.op3 = -32;
			goto re;
		}
		if (kbhit())
		{
			m = getch();
			switch (m)
			{
			case LEFT: left();
				break;
			case RIGHT: right();
				break;
			case 'q':exit(0);
			case 'Q':exit(0);

			}
		}
		else {
			delay(150 - car.level * 30);
		}
	}
	getch();
	return(0);
}

void help()
{
	cleardevice();
	settextstyle(7, 0, 6);
	setcolor(3 + 128);
	outtextxy(200, 10, "HELP");
	setfillstyle(9, 5);
	bar(180, 70, 330, 75);
	textcolor(YELLOW);
	gotoxy(3, 6);
	cout << "\n\n\n\n\n\n\nCAR SURFER-DRIVE TO THE FINISH is a simple game in which you have to lead a car by avoiding blocks on road.\nYou have to use the following keys to control the car\n\n->UP\t--\tForward movement\n->LEFT\t--\tLeftside movement\n->RIGHT\t--\tRightside movement\n->q\t--\tExit from game at any time\n";
	getch();
	return;
}

void menu()
{
	int s = 1, t = 0, a, p = 140;
	do
	{
		do
		{
			cleardevice();
			setcolor(YELLOW);
			settextstyle(8, 0, 4);
			outtextxy(230, 30, "MENU");
			setfillstyle(9, 2);
			bar(140, 80, 400, 82);
			settextstyle(3, 0, 1);
			setcolor(10);
			outtextxy(220, 137, "PLAY GAME");
			outtextxy(220, 167, "SCORES");
			outtextxy(220, 197, "HELP");
			outtextxy(220, 227, "ABOUT");
			outtextxy(220, 257, "EXIT");
			setcolor(RED);
			rectangle(200, p, 350, p + 20);
			a = getch();
			switch (a)
			{
			case UP:if (p > 140)
			{
				p -= 30;
				s--;
			}
					else
					{
						p = 260;
						s = 5;
					}
					break;
			case DOWN:if (p < 260)
			{
				p += 30;
				s++;
			}
					  else
					  {
						  p = 140;
						  s = 1;
					  }
					  break;
			case ENTER:a = 1;
				break;
			default:break;
			}
		} while (a != 1);
		switch (s)
		{
		case 1:play();
			break;
		case 2:disp_score();
			break;
		case 3:help();
			break;
		case 4:about();
			break;
		case 5:exit(0);
		default:cprintf("Invalid choice");
			break;
		}
	} while (t == 0);
	getch();
}

void main()
{
	int gd = DETECT, gm, errorcode;
	initgraph(&gd, &gm, "c:\\tc\\BGI");
	cleardevice();
	logo();
	menu();
	closegraph();
}
