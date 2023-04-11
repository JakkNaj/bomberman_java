# This REPOSITORY is for semestral work requested in Programming in Java course (FEL CVUT).
## THEME: game engine on retro game BOMBERMAN - single player

## Used Technologies:
- Programming language: Java version 19.0.2
- Graphics: Swing
- Testing: JUnit
- 
## User Manual: 
#### in ENGLISH (in CZECH below english section)
#### Starting the game
After starting the game, you will see the main menu screen with options. You will be able to choose the difficulty level you want to play: hard - 1 life, medium - 3 lives, easy - 5 lives.
#### Controls
The player can move around the playing field using the arrow keys on the keyboard. By pressing the spacebar, the player can place a bomb at their current position. The player can have only one bomb placed on the playing field at a time, unless they collect a power-up that allows them to have more bombs.
#### Playing field
The playing field consists of players, walls, and items. Players can use bombs to destroy light walls and collect items that improve their abilities, such as speed, number of bombs, etc.
#### Objective
The objective of the game is to find the gate to the next level, which is hidden behind one of the destructible wall blocks. Only by killing all enemies can the player pass through this gate, and they must do so within a certain time limit. If the time runs out, the player loses a life. If the player dies but still has lives left, they will respawn at the beginning of the level where they died. The player can defeat enemies by using bombs, which can destroy light walls and hit enemies.
#### End of the game
The game ends when the player runs out of lives (the player dies by colliding with an enemy, getting hit by their own bomb, or when the level time runs out), or when they successfully pass through all five levels and defeat all enemies.
#### Restarting the game
After the game is over, a menu of options will appear asking if you want to play again or quit the game.

### Object Scheme - english:
#### Class Game
This class will take care of generic behavior and functioning of the game.
There will be methods for loading and saving levels, creating a player and their movement on the game board, obtaining power-ups, creating bombs, and recording scores.
#### Class Player
This class will represent the player in the game.
The class will have attributes such as position, movement speed, number of bombs, bomb strength, and methods for manipulating these attributes.
#### Class Tile
This class will represent a single tile on the game board.
The class will have attributes such as position, tile type, and methods for manipulating these attributes.
#### Class Level
This class will represent a single level of the Bomberman game.
The class will have attributes such as the game board, number of enemies, number of items, level specifications, and methods for manipulating these attributes.
#### Class GameBoard
This class will represent the game board within a single level. It will be a two-dimensional array of Tile objects.
#### Class Launcher
This class will be responsible for launching the game. It will contain the main method and handle loading levels and starting the game.
#### Class Display
This class will handle creating the window in which the game will be rendered.

## Použité technologie:
- Programovací jazyk: Java verze 19.0.2
- Grafika: Swing
- Testování: JUnit

## Uživatelská přířučka:
#### Spuštění hry
Po spuštění hry uvidíte úvodní obrazovku s nabídkou možností. Budete si moct zvolit, jakou obtížnost chcete hrát: težká - 1 život, střední - 3 životy, jednoduchá - 5 životů.
#### Ovládání
Hráč se může pohybovat po hracím poli pomocí šipek na klávesnici. Stisknutím klávesy mezerník umístí bombu na pozici hráče. Hráč může mít maximálně jednu bombu umístěnou na hracím poli najednou, pokud nesebere power-up, který mu umožní mít bomb víc.
#### Hrací pole
Na hracím poli se nacházejí hráči, zdi a předměty. Hráči mohou použít bomby k ničení lehkých zdí a sbírání předmětů, které zlepší jejich schopnosti, jako například rychlost pohybu, počet bomb apod.
#### Cíl hry
Cílem hry je najít bránu do další úrovně, která je skrytá za některým ze zničitelných bloků zdí. Touto bránou může projít pouze po zabití všech nepřátel a to musí stihnout za určitý čas, pokud mu čas vyprší, tak ztratí život. Pokud hráč umře a ještě mu zbývají životy, tak se vrátí na začátek úrovně, ve které zemřel. Hráč může porazit nepřátele pomocí bomb, které mohou zničit lehké zdi a zasáhnout protivníky.
#### Konec hry
Hra končí, když hráči již nezbývají životy(hráč umře, když se srazí s protivníkem, nebo ho zasáhne vlastní bomba, nebo mu vyprší čas úrovně), nebo projde všemi 5 úrovněmi a úspěšně zabije všechny nepřátele.
#### Znovuspouštění hry
Po skončení hry se objeví nabídka možností, jestli chcete hrát znovu nebo ukončit hru.

### Objektový návrh česky:
#### Třída Game
Tato třída se bude starat o generické chování a fungování hry.
V této třídě budou metody pro načítání a ukládání levelů, vytváření hráče a jeho pohyb po hracím poli, získávání power-upů, vytváření bomb a zaznamenávání scóre.
#### Třída Player
Tato třída bude reprezentovat hráče ve hře. Tato třída bude mít atributy jako pozice, rychlost pohybu, počet bomb a sílu bomb a metody pro manipulaci s těmito atributy.
#### Třída Tile
Tato třída bude reprezentovat jedno políčko na hracím poli. Tato třída bude mít atributy, jako jsou pozice, typ políčka a metody pro manipulaci s těmito atributy.
#### Třída Level
Tato třída bude reprezentovat jednu úroveň hry Bomberman. Tato třída bude mít atributy, jako jsou hrací pole, počet nepřátel, počet předmětů a specifikace úrovně a metody pro manipulaci s těmito atributy.
#### Třída GameBoard
Tato třída bude reprezentovat hrací pole v rámci jedné úrovně. Bude to dvourozměrné pole objektů typu Policko.
#### Třída Launcher
Tato třída bude třída, která se star spuštění hry. Bude obsahovat hlavní metodu a bude se starat o načítání úrovní a spouštění hry.
#### Třída Display
Tato třída se bude starat o vytvoření okna, do kterého se bude vykreslovat hra.

