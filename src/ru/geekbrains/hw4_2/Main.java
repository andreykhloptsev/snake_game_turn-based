package ru.geekbrains.hw4_2;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static boolean loseFlag=false; //меняется на true когда врезаемся в себя
    public static boolean pointFlag=false; //меняется на true когда подбираем точку
    public static final int SIZE = 7; //размер поля
    //размер массива [количество клеточек на поле][длина одной строки пол
    public static int[][] snakeArray = new int[SIZE*SIZE][SIZE];
    //размер массива [длина строки][длина столбца]
    public static char[][] map= new char[SIZE][SIZE];
    //точка к которой идем
    public static int point[] = {-2,-2};
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();
    public static int snakeArrayLength =2;//длина змейки



    public static void main(String[] args) {

        System.out.println("Добро пожаловать в игру Змейка!");
        System.out.println("Голова змейки обозначается 'o'");
        System.out.println("Искомая точка обозначается 'X'");
        System.out.println("Сквозь стенки можно ходить");
        System.out.println("Удачной игры!");
        initMap();
        newSnake();
        newPoint();
        drawSnake();
        while (!loseFlag){
            snakeMove();
            if (pointFlag)
            {
                newPoint();
                pointFlag=false;
            }
            drawSnake();
        }
        System.out.println("Твой результат: "+snakeArrayLength);
    }
    //инициализация змейки
    public static void newSnake()
    {
        for (int i = 0; i <snakeArray.length; i++) {
            for (int j = 0; j <SIZE ; j++) {
                    snakeArray[i][j]=-1;
            }
        }
        int snakeArrayX= random.nextInt(SIZE);
        int snakeArrayY= random.nextInt(SIZE);
        //заполнение головы змейки
        snakeArray[0][snakeArrayX]=snakeArrayY;
        //заполнение тела змейки
        if (snakeArrayX<SIZE-1)
        {
            snakeArray[1][snakeArrayX+1]=snakeArrayY;
        } else
        {
            snakeArray[0][snakeArrayX]=-1;
            snakeArray[0][snakeArrayX-1]=snakeArrayY;
            snakeArray[1][snakeArrayX]=snakeArrayY;
        }
    }
    //инициализация новой точки
    public static void newPoint()
    {
        //определение точки цели
        do {
            point[0]=random.nextInt(SIZE);
            point[1]=random.nextInt(SIZE);
        }while (isSnake(point[0],point[1]));
        map[point[0]][point[1]]='X';
    }


    //инициализация карты
    public static void initMap()
    {
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                map[i][j]='_';
            }
        }
    }
    //метод проверяющий не является ли данная точка частью змейкой
    public static boolean isSnake(int x, int y)
    {
        for (int i = 0; i <snakeArrayLength ; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (snakeArray[i][j] == y && j == x) {
                    return true;
                }
            }
        }
        return false;
    }
    //метод прорисовывающий змейку и точку, к которой надо ползти
    public static void drawSnake()
    {
        initMap();
        map[point[0]][point[1]]='X';
        for (int i = 0; i <snakeArrayLength; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (snakeArray[i][j] != -1) {
                    if (i > 0) {
                        map[j][snakeArray[i][j]] = '*';
                    } else {
                        map[j][snakeArray[i][j]] = 'o';
                    }
                }
            }
        }
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    //метод описывающий передвижение змейки
    public static void snakeMove()
    {
        System.out.println("Выедите направление движения змейки.");
        System.out.println("Управление : WASD");
        char move = scanner.next().charAt(0);
        //точные координаты головы змейки
        int snakeArrayX=0, snakeArrayY=0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (map[i][j]=='o') {
                    snakeArrayX = i;
                    snakeArrayY = j;
                }
            }
        }
        switch (move) {
            case 'w':
                //проверка прохода сквозь стену
                if (snakeArrayX-1<0)
                {snakeArrayX=SIZE;}
                //проверка достижения точки
                if (snakeArrayX-1==point[0]&&snakeArrayY==point[1]) {
                    snakeArrayLength++;
                    pointFlag=true;
                    map[point[0]][point[1]]='_';
                }    //проверка не является ли частью змеи
                if (isSnake(snakeArrayX-1, snakeArrayY))
                    {
                        System.out.println("You lose");
                        loseFlag=true;
                        break;
                    }
                else {
                    for (int i = snakeArrayLength-1; i >0 ; i--) {
                        for (int j = 0; j <SIZE ; j++) {
                            snakeArray[i][j]=snakeArray[i-1][j];
                        }
                    }
                        snakeArray[0][snakeArrayX%SIZE]=-1;
                        snakeArray[0][snakeArrayX-1]=snakeArrayY;
                }
                break;
            case 's':
                if (snakeArrayX+1==SIZE)
                    {snakeArrayX=-1;}
                if (snakeArrayX+1==point[0]&&snakeArrayY==point[1]){
                    snakeArrayLength++;
                    pointFlag=true;
                    map[point[0]][point[1]]='_';
                }
                //проверка не является ли частью змеи
                if (isSnake(snakeArrayX+1, snakeArrayY))
                {
                    System.out.println("You lose");
                    loseFlag=true;
                    break;
                }
                else {
                    for (int i = snakeArrayLength-1; i >0 ; i--) {
                        for (int j = 0; j <SIZE ; j++) {
                            snakeArray[i][j]=snakeArray[i-1][j];
                        }
                    }
                    snakeArray[0][(snakeArrayX+SIZE)%SIZE]=-1;
                    snakeArray[0][snakeArrayX+1]=snakeArrayY;
                }
                break;
            case 'a':
                if (snakeArrayY-1<0)
                {snakeArrayY=SIZE;}
                if (snakeArrayX==point[0]&&snakeArrayY-1==point[1]){
                    snakeArrayLength++;
                    pointFlag=true;
                    map[point[0]][point[1]]='_';
                }
                //проверка не является ли частью змеи
                if (isSnake(snakeArrayX, snakeArrayY-1))
                {
                    System.out.println("You lose");
                    loseFlag=true;
                    break;
                }
                else {
                    for (int i = snakeArrayLength-1; i >0 ; i--) {
                        for (int j = 0; j <SIZE ; j++) {
                            snakeArray[i][j]=snakeArray[i-1][j];
                        }
                    }
                    snakeArray[0][snakeArrayX]=-1;
                    snakeArray[0][snakeArrayX]=snakeArrayY-1;
                }
                break;
            case 'd':
                if (snakeArrayY+1==SIZE)
                {snakeArrayY=-1;}
                if (snakeArrayX==point[0]&&snakeArrayY+1==point[1]){
                    snakeArrayLength++;
                    pointFlag=true;
                    map[point[0]][point[1]]='_';
                }
                //проверка не является ли частью змеи
                if (isSnake(snakeArrayX, snakeArrayY+1))
                {
                    System.out.println("You lose");
                    loseFlag=true;
                    break;
                }
                else {
                    for (int i = snakeArrayLength-1; i >0 ; i--) {
                        for (int j = 0; j <SIZE ; j++) {
                            snakeArray[i][j]=snakeArray[i-1][j];
                        }
                    }
                    snakeArray[0][snakeArrayX]=-1;
                    snakeArray[0][snakeArrayX]=snakeArrayY+1;
                }
                break;
                default:
                    System.out.println("Управление WASD попробуйте еще раз");
        }
    }
}
