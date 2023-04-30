package com.example.telegrammbot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.example.telegrammbot.MenuMaker.MenuMakerCat;
import com.example.telegrammbot.MenuMaker.MenuMakerDog;
import com.example.telegrammbot.configuration.MenuCatDescription;
import com.example.telegrammbot.configuration.MenuDogDescription;
import com.example.telegrammbot.exception.UsersNullParameterValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * The Telegram bot update
 *
 * @author zalex14
 */
@Component
public class TBotListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TBotListener.class);
    private final TelegramBot telegramBot;
    private final MenuMakerDog menuMakerDog;

    private final MenuMakerCat menuMakerCat;

    int NUMBER_CHARACTERS = 2048; // Max символов считываемое из файла
    private boolean isWaitingUserData; //ожидаем сообщение с данными пользователя после нажатия кнопки "записать данные пользователя"
    private boolean isPhoto;           //ожидаем загрузку фото
    private Long flagChoosingShelter; // Флаг выбора приюта
    private String startLine;

    public TBotListener(TelegramBot telegramBot, MenuMakerDog menuMakerDog, MenuMakerCat menuMakerCat) {
        this.telegramBot = telegramBot;
        this.menuMakerDog = menuMakerDog;
        this.menuMakerCat = menuMakerCat;
    }

    //  @PostConstruct
    // public void init() {
    //    telegramBot.setUpdatesListener(this);
    //}//
    @PostConstruct
    public void init() {
        menuButton();
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .forEach(update -> {
                    logger.info("Processing update: {}", update);
                    if (update.message() != null && update.message().text() != null || update.message() != null && update.message().photo() != null) {
                        Long chatId = update.message().chat().id();
                        processUpdate(chatId, update);

                    }
                    if (update.callbackQuery() != null) {
                        Long chatId = update.callbackQuery().message().chat().id();
                        startMenuDogs(chatId, update);
                        callBackUpdateMenu1(chatId, update);
                        callBackUpdateMenu2(chatId, update);
                        callBackDataListOfRecommendations(chatId, update);
                        startMenuCats(chatId, update);
                        menu1KeyboardCats(chatId, update);
                           menu2KeyboardCats(chatId, update);
                          listOfDocumentsForCats(chatId, update);
                    }
                });
        logger.info("Processing update: {}", updates);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void menuButton() {
        SetMyCommands message = new SetMyCommands(
                new BotCommand("/start", "Запустить бота"));
        telegramBot.execute(message);
    }


    private void processUpdate(Long chatId, Update update) {
        String userMessage = update.message().text();
        if (isWaitingUserData) {
            try {
                createUser(chatId, userMessage);
                telegramBot.execute(new SendMessage(chatId, "Данные успешно записаны"));
                isWaitingUserData = false;
            } catch (UsersNullParameterValueException e) {
                telegramBot.execute(new SendMessage(chatId, "Не удается распознать данные: " + e.getMessage() + ", попробуйте еще раз."));
            }
        } else if (isPhoto) {
//            try {
//                reportUsersService.uploadReportUser(update); // Сохранение отчета User
//                telegramBot.execute(new SendMessage(chatId, "Изображение успешно сохранено"));
//                isPhoto = false;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        } else {
            if (userMessage.equals("/start")) {
                startLine = greetings(chatId, update);
                telegramBot.execute(new SendMessage(chatId, startLine + ", Выберите приют").replyMarkup(menuMakerDog.startMenuKeyboard()));
            } else {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы ввели что-то странное!"));
            }
        }
    }

    private void startMenuDogs(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();
            if (data.equals(MenuDogDescription.DOGSHELTERENTER.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали собачий приют").replyMarkup(menuMakerDog.afterStartDogKeyBoard()));
            } else if (data.equals(MenuDogDescription.AboutPetShelter.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали пункт выбрать информацию о приюте").replyMarkup(menuMakerDog.menu1Keyboard()));
            } else if (data.equals(MenuDogDescription.HOWTOTAKEDOG.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали пункт как взять информацию о питомце").replyMarkup(menuMakerDog.menu2Keyboard()));
            } else if (data.equals(MenuDogDescription.SENDDOGPHOTO.name())) {
                telegramBot.execute(new SendMessage(chatId, "Загрузите отчет").replyMarkup(menuMakerDog.menu3Keyboard()));
                isPhoto = true;
            }
        }

    }

    public String greetings(Long chatId, Update update) {
        String firstName = update.message().chat().firstName();
        String lastName = update.message().chat().lastName();
        return firstName + " " + lastName;
    }

    public void createUser(Long chatId, String str) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Current Method is - " + methodName);
        //  logger.info("Метод" + Object.class.getEnclosingMethod());
        String[] strDivided = str.split("\\s*(\\s|,|!|\\.)\\s*"); // Разбивка строки данных пользователя
//        User user = new User();
//        user.setFirstName(strDivided[0]); // Если нет данных вылезает исключение ArrayIndexOutOfBoundsException
//        user.setLastName(strDivided[1]);
//        user.setUserPhoneNumber(strDivided[2]);
//        user.setUserEmail(strDivided[3]);
//        usersService.createUserInDb(user);
    }

    private void callBackUpdateMenu1(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();

            if (data.equals(MenuDogDescription.WRITECONTACS.name())) {
                isWaitingUserData = true;
                telegramBot.execute(new SendMessage(chatId,
                        "Введите данные пользователя в формате \"Имя Фамилия Телефон Почта (через пробел)\""));
            } else if (data.equals(MenuDogDescription.AboutPetShelterDocx.name())) {
                flagChoosingShelter = 1L;
                aboutTheShelter(chatId);
            } else if (data.equals(MenuDogDescription.SCHEDULE.name())) {
                sendLocationPhoto(chatId);
            } else if (data.equals(MenuDogDescription.VOLUNTEERCALL.name())) {
                telegramBot.execute(new SendMessage(chatId, "Волонтер позван"));
            }
            if (data.equals(MenuDogDescription.SAFETYRULES.name())) {
                //     fileRead(chatId, NUMBER_CHARACTERS, DOGSHELTER_SAFETY_FILE);
            }
        }
    }

    private void aboutTheShelter(long chatId) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Current Method is - " + methodName);

        //  Shelters shelters = new Shelters();
        // shelters = shelterService.getShelter(flagChoosingShelter);
        //telegramBot.execute(new SendMessage(chatId, shelters.getDescriptionShelter()));
    }

    public void sendLocationPhoto(Long chatId) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Current Method is - " + methodName);
        float latitude = (float) 59.82225018231752;
        float longitude = (float) 30.178212453672643;
        telegramBot.execute(new SendMessage(chatId, "Мы работаем с 08:00 до 23:00"));
        //  java.io.File file = new File(DOG_SHELTER_NURSERY_LOCATION);
        //   telegramBot.execute(new SendPhoto(chatId, file));
        telegramBot.execute(new SendMessage(chatId, "Или используйте навигатор"));
        SendLocation location = new SendLocation(chatId, latitude, longitude);
        telegramBot.execute(location);
    }

    private void startMenuCats(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();
            if (data.equals(MenuDogDescription.CATSHELTERENTER.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали кошачий приют").replyMarkup(menuMakerCat.afterStartCatKeyBoard()));
            } else if (data.equals(MenuCatDescription.AboutCatPetShelter.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали раздел о приюте").replyMarkup(menuMakerCat.menu1KeyboardCat()));
            } else if (data.equals(MenuCatDescription.CatVolunteer.name())) {
                telegramBot.execute(new SendMessage(chatId, "Волонтер позван"));
            } else if (data.equals(MenuCatDescription.HOWTOTAKECAT.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали раздел как взять животное").replyMarkup(menuMakerCat.menu2KeyboardCat()));
            } else if (data.equals(MenuCatDescription.SENDPETREPORT.name())) {
                telegramBot.execute(new SendMessage(chatId, "Загрузите отчет").replyMarkup(menuMakerDog.menu3Keyboard()));
                isPhoto = true;
            }
        }
    }


    private void callBackUpdateMenu2(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();
            if (data.equals(MenuDogDescription.MENULISTOFDOCUMENTS.name())) {
                telegramBot.execute(new SendMessage(chatId, "Список документов").replyMarkup(menuMakerDog.inlineButtonsListOfRules(chatId)));
            } else if (data.equals(MenuDogDescription.CYNOLOGISTADVICE.name())) {
                //  fileRead(chatId, NUMBER_CHARACTERS, CYNOLOGIST_ADVICE);
                telegramBot.execute(new SendMessage(chatId, "Ознакомьтесь с правилам обращения с собакой или выберите кинолога:").replyMarkup(menuMakerDog.buttonCynologist(chatId)));
            } else if (data.equals(MenuDogDescription.WRITECONTACS.name())) {
                isWaitingUserData = true;
            } else if (data.equals(MenuDogDescription.DOGMEETINGRULES.name())) {
                //  fileRead(chatId, NUMBER_CHARACTERS, DOG_MEETING_RULES);
            }
        }
    }

    private void callBackDataListOfRecommendations(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();
            if (data.equals(MenuDogDescription.DOCSTOTAKEDOG.name())) {
                //    sendPetsDocuments(chatId);
            } else if (data.equals(MenuDogDescription.DOGTRASPORTATION.name())) {
                //    fileRead(chatId, NUMBER_CHARACTERS, DOGS_TRANSPORTATION);
            } else if (data.equals(MenuDogDescription.HOMEFORPUPPEY.name())) {
                //      fileRead(chatId, NUMBER_CHARACTERS, HOME_FOR_PUPPY);
            } else if (data.equals(MenuDogDescription.HOMEFORADULTDOG.name())) {
                //    fileRead(chatId, NUMBER_CHARACTERS, HOME_FOR_ADULT_DOG);
            } else if (data.equals(MenuDogDescription.LIMITEDDOG.name())) {
                //    fileRead(chatId, NUMBER_CHARACTERS, FOR_LIMITED_DOGS);
            } else if (data.equals(MenuDogDescription.REFUSE.name())) {
                //   fileRead(chatId, NUMBER_CHARACTERS, REFUSE);
            }
        }
    }

    private void menu1KeyboardCats(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();
            if (data.equals(MenuCatDescription.AboutCatPetShelterDocx.name())) {
                flagChoosingShelter = 2L;
                aboutTheShelter(chatId);
            } else if (data.equals(MenuCatDescription.CATNURSERYLOCATION.name())) {
                sendCatNurseryLocationPhoto(chatId);
            } else if (data.equals(MenuCatDescription.WRITECONTACTSCAT.name())) {
                isWaitingUserData = true;
                telegramBot.execute(new SendMessage(chatId,
                        "Введите данные пользователя в формате \"Имя Фамилия Телефон Почта (через пробел)\""));
            }
        }
    }

    public void sendCatNurseryLocationPhoto(Long chatId) {
//        String methodName = new Object() {
//        }
//                .getClass()
//                .getEnclosingMethod()
//                .getName();
//        logger.info("Current Method is - " + methodName);
//        float latitude = (float) 60.01192431352728;
//        float longitude = (float) 29.721648774315117;
//        telegramBot.execute(new SendMessage(chatId, "Мы работаем с 08:00 до 22:00"));
//        java.io.File file = new File(CAT_SHELTER_NURSERY_LOCATION);
//        telegramBot.execute(new SendPhoto(chatId, file));
//        telegramBot.execute(new SendMessage(chatId, "Или используйте навигатор"));
//        SendLocation location = new SendLocation(chatId, latitude, longitude);
//        telegramBot.execute(location);
    }

    private void menu2KeyboardCats(Long chatId, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() != null) {
            String data = callbackQuery.data();
            if (data.equals(MenuCatDescription.CATDOCUMENTS.name())) {
                telegramBot.execute(new SendMessage(chatId, startLine + ", Вы выбрали списки документов:").replyMarkup(menuMakerCat.inlineCatButtonsListOfRules(chatId)));
            } else if (data.equals(MenuCatDescription.CATMEETINGRULES.name())) {
        //        fileRead(chatId, NUMBER_CHARACTERS, CAT_MEETING_RULES);
            }
        }
    }

    private void listOfDocumentsForCats(Long chatId, Update update) {
//        CallbackQuery callbackQuery = update.callbackQuery();
//        if (update.callbackQuery() != null) {
//            String data = callbackQuery.data();
//            if (data.equals(MenuCatDescription.DOCSTOTAKECAT.name())) {
//                fileRead(chatId, NUMBER_CHARACTERS, DOCS_TO_TAKE_CAT);
//            } else if (data.equals(MenuCatDescription.CATTRANSPORTATIONRULES.name())) {
//                fileRead(chatId, NUMBER_CHARACTERS, CAT_TRANSPORTATION);
//            } else if (data.equals(MenuCatDescription.HOMEFORKITTEN.name())) {
//                fileRead(chatId, NUMBER_CHARACTERS, HOME_FOR_KITTEN);
//            } else if (data.equals(MenuCatDescription.HOMEFORADULTCAT.name())) {
//                fileRead(chatId, NUMBER_CHARACTERS, HOME_FOR_ADULT_CAT);
//            } else if (data.equals(MenuCatDescription.FORLIMITEDCATS.name())) {
//                fileRead(chatId, NUMBER_CHARACTERS, FOR_LIMITED_CATS);
//            } else if (data.equals(MenuCatDescription.CATREFUSE.name())) {
//                fileRead(chatId, NUMBER_CHARACTERS, CAT_REFUSE);
//            }
//        }
    }



}