package ali.project.railway_project.Component;

import ali.project.railway_project.Config.BotConfig;
import ali.project.railway_project.Entity.Shikoyatlar;
import ali.project.railway_project.Entity.User;
import ali.project.railway_project.Entity.BotEslatma;
import ali.project.railway_project.Repository.BotEslatmaRepo;
import ali.project.railway_project.Repository.RoleRepo;
import ali.project.railway_project.Repository.ShikoyatlarRepo;
import ali.project.railway_project.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    private final RoleRepo roleRepo;

    private final BotConfig botConfig;

    private final BotEslatmaRepo botEslatmas;
    private final ShikoyatlarRepo shikoyatlarRepo;

    Map<Long, String> language = new HashMap<>();
    Map<Long, String> isRegister = new HashMap<>();
    Map<Long, String> name = new HashMap<>();
    Map<Long, String> surname = new HashMap<>();
    Map<Long, String> phoneNumber = new HashMap<>();

    Map<Long, String> email = new HashMap<>();
    Map<Long, String> isShikoyat = new HashMap<>();


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (update.hasMessage()) {
            Contact contact = message.getContact();
            Long chatId = message.getChatId();
            String text = message.getText();
            if (message.hasText()) {
                if (text.equals("/start")) {
                    if (!userRepository.existsUserByChatId(chatId)) {
                        getButton(chatId, "salom Uzbekistan Temir yo'llari botiga hush kelibsiz.Tilni tanlang");
                    } else {
                        service(chatId, language.get(chatId).equals("uzb") ? "salom botga hush kelibsiz" : "привет и добро пожаловать в бота");
                    }
                } else if (text.equals("Shikoyat qoldirish") || text.equals("Оставить жалобу")) {
                    sendMsg(chatId, language.get(chatId).equals("uzb") ? "Salom botga uzingizni shikoyatingizni qoldirishingiz mumkin. Sizning shikoyatingiz adminlar tomonidan ko'rib chiqiladi" : "Вы можете оставить свою жалобу боту Hello. Ваша жалоба будет рассмотрена администраторами");
                    isShikoyat.put(chatId, "message");
                } else if (isShikoyat.size() > 0) {
                    shikoyat(chatId, text);
                }
                List<BotEslatma> all = botEslatmas.findAll();
                if (all.size() > 0) {
                    for (BotEslatma botEslatma : all) {
                        sendMsg(botEslatma.getChatId(), language.get(botEslatma.getChatId()).equals("uzb") ? botEslatma.getMessage() : botEslatma.getMessageRus());
                        botEslatmas.deleteById(botEslatma.getId());
                    }
                } else if (isRegister.size() > 0) {
                    register(chatId, text);
                }
            } else if (message.hasContact()) {
                if (isRegister.get(chatId).equals("phoneNumber")) {
                    if (!userRepository.existsUserByPhoneNumberEqualsIgnoreCase(contact.getPhoneNumber())) {
                        emailButton(chatId, language.get(chatId).equals("uzb") ? "sizda email pochta mavjudmi" : "У вас есть адрес электронной почты?");
                        isRegister.remove(chatId);
                        phoneNumber.put(chatId, contact.getPhoneNumber());
                        isRegister.put(chatId, "email");
                    } else {
                        phoneButton(chatId, language.get(chatId).equals("uzb") ? "uzur bu telefon raqam bizning ma'lumotlar bazasida mavjud boshqa telefon raqam orqali kirishga urunib ko'ring" : "Извините, этот номер телефона есть в нашей базе данных, попробуйте получить доступ к другому номеру телефона");
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String data = callbackQuery.getData();
            Long chatId = callbackQuery.getMessage().getChatId();
            if (data.equals("rus")) {
                language.put(chatId, "rus");
                sendMsg(chatId, "введите ваше имя");
                isRegister.put(chatId, "name");
            } else if (data.equals("uzb")) {
                language.put(chatId, "uzb");
                sendMsg(chatId, "ismingizni kiriting");
                isRegister.put(chatId, "name");
            } else if (data.equals("ha")) {
                sendMsg(chatId, language.get(chatId).equals("uzb") ? "bizga emailingizni yuboring" : "отправьте нам свой адрес электронной почты");
                isRegister.remove(chatId);
                isRegister.put(chatId, "emailHa");
            } else if (data.equals("yo'q")) {
                User user = User.builder()
                        .name(name.get(chatId))
                        .lastName(surname.get(chatId))
                        .role(Collections.singletonList(roleRepo.findById(1).get()))
                        .phoneNumber(phoneNumber.get(chatId))
                        .chatId(chatId)
                        .build();
                if (!userRepository.existsUserByPhoneNumberEqualsIgnoreCase(user.getPhoneNumber())) {
                    sendMsg(chatId, language.get(chatId).equals("uzb") ? "sizning ma'lumotlaringiz saqlab qolindi\nEndi siz ushbu bot orqali o'zingizga kelgan kiyimlar vaqti haqida ma'lumot olishingiz mumkin" : "ваша информация сохранена\n" +
                            "Теперь вы можете получать информацию о времени прихода одежды через этого бота");
                    userRepository.save(user);
                    isRegister.remove(chatId);
                } else {
                    service(chatId, language.get(chatId).equals("uzb") ? "salom botga hush kelibsiz" : "привет и добро пожаловать в бота");
                }
            }
        }

    }

    public void sendMsg(Long chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            System.err.println("xabar bormadi");
        }
    }

    public void shikoyat(Long chatId, String text) {
        if (isShikoyat.get(chatId).equals("message")) {
            isShikoyat.clear();
            Optional<User> userByChatId = userRepository.findUserByChatId(chatId);
            if (userByChatId.isPresent()) {
                User user = userByChatId.get();
                Shikoyatlar build = Shikoyatlar.builder()
                        .message(text)
                        .userPhone(user.getPhoneNumber())
                        .build();
                build.setName(user.getName());
                shikoyatlarRepo.save(build);
                sendMsg(chatId, language.get(chatId).equals("uzb") ? "Sizning shikoyatingiz qabul qilindi" : "\n" +
                        "Ваша жалоба получена");
            }
        }
    }

    public void emailButton(Long chatId, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> button = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton.setText(language.get(chatId).equals("uzb") ? "email mavjud" : "Электронная почта доступна");
        inlineKeyboardButton.setCallbackData("ha");
        inlineKeyboardButton1.setText(language.get(chatId).equals("uzb") ? "menda email yo'q" : "у меня нет электронной почты");
        inlineKeyboardButton1.setCallbackData("yo'q");
        button.add(inlineKeyboardButton);
        button.add(inlineKeyboardButton1);
        buttons.add(button);
        inlineKeyboardMarkup.setKeyboard(buttons);
        try {
            execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(text)
                            .replyMarkup(inlineKeyboardMarkup)
                            .build()
            );
        } catch (TelegramApiException e) {
            sendMsg(chatId, "ma'lumot topilmadi");
        }
    }

    public void getButton(Long chatId, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> button = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Rus");
        inlineKeyboardButton.setCallbackData("rus");
        inlineKeyboardButton1.setText("Uzb");
        inlineKeyboardButton1.setCallbackData("uzb");
        button.add(inlineKeyboardButton);
        button.add(inlineKeyboardButton1);
        buttons.add(button);
        inlineKeyboardMarkup.setKeyboard(buttons);
        try {
            execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(text)
                            .replyMarkup(inlineKeyboardMarkup)
                            .build()
            );
        } catch (TelegramApiException e) {
            sendMsg(chatId, "ma'lumot topilmadi");
        }
    }

    public void phoneButton(Long chatId, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        row.add(keyboardButton);
        keyboardButton.setText(language.get(chatId).equals("uzb") ? "telefon raqam" : "номер телефона");
        keyboardButton.setRequestContact(true);
        rows.add(row);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        isRegister.remove(chatId);
        isRegister.put(chatId, "phoneNumber");
        try {
            execute(SendMessage.builder()
                    .replyMarkup(replyKeyboardMarkup)
                    .chatId(chatId)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            sendMsg(chatId, language.get(chatId).equals("uzb") ? "botda hatolik" : "\n" +
                    "ошибка бота");
        }
    }

    public void register(Long chatId, String text) {
        if (isRegister.get(chatId).equals("name")) {
            sendMsg(chatId, language.get(chatId).equals("uzb") ? "familyangizni kiriting" : "\n" +
                    "введите вашу фамилию");
            isRegister.remove(chatId);
            name.put(chatId, text);
            isRegister.put(chatId, "surname");
        } else if (isRegister.get(chatId).equals("surname")) {
            phoneButton(chatId, language.get(chatId).equals("uzb") ? "bizga telefon raqamignizni ulashing" : "поделитесь с нами своим номером телефона");
            surname.put(chatId, text);
        } else if (isRegister.get(chatId).equals("emailHa")) {
            email.put(chatId, text);
            User user = new User(name.get(chatId), surname.get(chatId), phoneNumber.get(chatId), chatId, email.get(chatId), Collections.singletonList(roleRepo.findById(1).get()));
            if (!userRepository.existsUserByPhoneNumberEqualsIgnoreCase(user.getPhoneNumber())) {
                sendMsg(chatId, language.get(chatId).equals("uzb") ? "sizning ma'lumotlaringiz saqlab qolindi\nEndi siz ushbu bot orqali o'zingizga kelgan kiyimlar vaqti haqida ma'lumot olishingiz mumkin" : "\n" +
                        "ваша информация сохранена\n" +
                        "Теперь вы можете получать информацию о времени прихода одежды через этого бота");
                userRepository.save(user);
                isRegister.remove(chatId);
            } else {
                service(chatId, language.get(chatId).equals("uzb") ? "salom botga hush kelibisiz" : "Привет, добро пожаловать в бота");
            }
        }
    }

    public void service(Long chatId, String text) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(language.get(chatId).equals("uzb") ? "Shikoyat qoldirish" :
                "Оставить жалобу");
        rows.add(row);
        markup.setKeyboard(rows);
        markup.setResizeKeyboard(true);
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(markup)
                    .build());
        } catch (TelegramApiException e) {
            sendMsg(chatId, language.get(chatId).equals("uzb") ? "botda hatolik" : "\n" +
                    "ошибка бота");
        }
    }


}
