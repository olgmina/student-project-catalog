# Редактор блочных диаграмм

## Техническая информация
- Архитектура: MVC;
- Применённый паттерн: Builder (для создания диаграммы);
- View: JavaFX 15 (fxml and Java, minimum JavaFX 11);
- Back: Java 13 (minimum Java 10);
- Gradle: 4.12 (JVM 13, minimum Java 8);
- IDE: Intellij Idea Community Edition 2020.2.4 (minimum Intellij Idea Community Edition 2018).


Для запуска в Intellij Idea следует добавить конфигурацию (Edit Configuration -> Add New Configuration -> Application). Далее выполнить действия:
- установить в качестве точки входа в приложение (Edit Configuration -> Application -> Main class) класс `Main.java`;
- Загрузить JavaFX SDK 15(https://gluonhq.com/products/javafx/). Установить опции виртуальной машины Java (Edit Configuration -> Application -> VM Options) по следующему примеру: 
`--module-path "путь до библиотек JavaFX sdk(папка lib)" --add-modules=javafx.controls,javafx.fxml,javafx.graphics`;
- установить в качестве текущего модуля (Edit Configuration -> Application -> Use classpath of module) модуль `blocks-diagram.main`.

## Использование и скриншоты:
Есть возможность сохранения в файл и загрузки из него. Файл текстовый. Добавлять столбцы и строки можно бесконечно.
Взаимодействовать можно с главным меню и с таблицей, а именно её контекстным меню(ПКМ - правая кнопка мыши). С главным меню интуитивно понятно, а с таблицей по следующим правилам:
1. Если таблица пуста, то её контекстное меню содержит:
![1](screenshots/1.png)

2. Если в таблице нет строк, то её контекстное меню содержит:
![2](screenshots/2.png)

3. Если таблица не пуста, то добавить строку можно нажав ПКМ по строке:
![3](screenshots/3.png)

4. Если таблица не пуста, то добавить столбец можно нажав ПКМ по заголовку столбца:
![4](screenshots/4.png)

Скриншот работы:
![5](screenshots/5.png)

В комплекте в папке examples два примера файлов с данными.
## Приглашение к сотрудничеству
Загрузите исходные тексты или клонируйте их с помощью команды git clone в выбранный каталог: git clone [gh repo clone range-vs/blocks-diagram](gh repo clone range-vs/blocks-diagram) и создайте ветку
