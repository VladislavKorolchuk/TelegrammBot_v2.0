package com.example.telegrammbot.telegram.constant;

/**
 * The Bot menu
 */
public class TBotMenu {
    /**
     * the standard first greeting
     */
    public static final String GREETING = """
            Данный бот позволяет прямо сейчас выбрать пиют и познакомиться с питомцами, или пригласить волонтера:\s
            1 - Приют для кошек
            2 - Приют для собак
            0 - Позвать волонтера
            Жду комманды""";
    /**
     * select of the bot menu
     */
    public static final String SELECT_MENU = """
            ShelterPets бот готов к работе:\s
            1 - Узнать информацию о приюте
            2 - Как взять животное из приюта
            3 - Прислать дневной отчет о питомце
            0 - Позвать волонтера
             Жду комманды""";

    /**
     * select of the shelter's information
     */
    public static final String SHELTER_INFO = """
            Получить подробную информацию о приюте:\s
            1 - Рассказать о приюте
            2 - Расписание работы и адрес, схема проезда
            3 - Техника безопасности на территории
            4 - Контактные данные для связи
            0 - Позвать волонтера
             Жду комманды""";

    /**
     * select menu for the cat's shelter
     */
    public static final String CAT_INFO = """
            Получить подробную информацию о приюте:\s
            1 - правила первого знакомства с котом (кошкой)
            2 - Список необходимых документов для усыновления
            3 - Принять и записать контактные данные
            4 - Транспортировка кошки
            5 - Обустройство дома для взрослой кошки
            5 - Обустройство дома для котенка
            6 - Обустройство кошек с заболеваниями
            7 - Типичные причины отказа в усыновлении
            0 - Позвать волонтера
             Жду комманды""";

    /**
     * select menu for the dog's shelter
     */
    public static final String DOG_INFO = """
            Получить подробную информацию о приюте:\s
            1 - правила первого знакомства с ссобакой 
            2 - Список необходимых документов для усыновления
            3 - Советы кинолога
            4 - Рекомендованные кинологи
            4 - Транспортировка собаки
            5 - Обустройство дома для взрослой собаки
            5 - Обустройство дома для щенка
            6 - Обустройство собак с заболеваниями
            7 - Типичные причины отказа в усыновлении
            8 - Принять и записать контактные данные
            0 - Позвать волонтера
             Жду комманды""";
}