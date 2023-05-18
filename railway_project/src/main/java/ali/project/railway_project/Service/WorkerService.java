package ali.project.railway_project.Service;

import ali.project.railway_project.Entity.*;
import ali.project.railway_project.Entity.Enums.RoleName;
import ali.project.railway_project.Payload.*;
import ali.project.railway_project.Repository.BotEslatmaRepo;
import ali.project.railway_project.Repository.EslatmaRepo;
import ali.project.railway_project.Repository.RoleRepo;
import ali.project.railway_project.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;
    private final RoleRepo roleRepo;

    private final EslatmaRepo eslatmaRepo;

    private final BotEslatmaRepo botEslatmaRepo;


    public List<ResWorker> workers() {
        List<ResWorker> resWorkers = new ArrayList<>();
        List<ResClothe> resClothes = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole().get(0).getRoleName().equals(RoleName.USER)) {
                for (Clothe clothe : user.getClothe()) {
                    ResClothe build = ResClothe.builder()
                            .giveDay(clothe.getKiyimOlishvaqti().getDate())
                            .giveMonth(clothe.getKiyimOlishvaqti().getMonth())
                            .giveYear(clothe.getKiyimOlishvaqti().getYear())
                            .awaredDay(clothe.getEslatishVaqti().getDate())
                            .awaredMonth(clothe.getEslatishVaqti().getMonth())
                            .awaredYear(clothe.getEslatishVaqti().getYear())
                            .photoId(clothe.getPhotoId())
                            .id(clothe.getId())
                            .name(clothe.getName())
                            .build();
                    resClothes.add(build);
                }
                ResWorker build = ResWorker.builder()
                        .id(user.getId())
                        .clothe(resClothes)
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .lastName(user.getLastName())
                        .name(user.getName())
                        .chatId(user.getChatId())
                        .build();
                resWorkers.add(build);
                Date date = new Date();
                if (user.getClothe() != null) {
                    for (Clothe clothe : user.getClothe()) {
                        if ((clothe.getKiyimOlishvaqti().getMonth() == date.getMonth()) && (clothe.getKiyimOlishvaqti().getYear() == date.getYear())) {
                            Eslatma eslatma;
                            int date1 = clothe.getKiyimOlishvaqti().getDate();
                            int month = clothe.getKiyimOlishvaqti().getMonth() + 1;
                            int year = clothe.getKiyimOlishvaqti().getYear() + 1900;
                            eslatma = Eslatma.builder()
                                    .message(user.getName() + " ismli ishchining " + clothe.getName() + " nomli kiyim olish oyi keldi.Ushbu ishchining kiyim olish vaqti: " + date1 + "-kun," + month + "-oy," + year + "-yil")
                                    .kun(date1)
                                    .oy(month)
                                    .year(date.getYear() + 1900)
                                    .userPhone(user.getPhoneNumber())
                                    .build();
                            eslatma.setName(user.getName());
                            if (!eslatmaRepo.existsEslatmaByMessage(eslatma.getMessage())) {
                                eslatmaRepo.save(eslatma);
                                BotEslatma botEslatma = BotEslatma.builder()
                                        .message("Salom sizning kiyim olish oyingiz keldi.Sizning kiyim olish vaqtingiz:" + date1 + "-kun" + month + "-oy" + year + "-yil")
                                        .messageRus("Здравствуйте, месяц сбора одежды наступил. Время сбора одежды:" + date1 + "-kun" + month + "-oy" + year + "-yil")
                                        .kiyimOlishVaqti(date)
                                        .kiyimNomi(clothe.getName())
                                        .chatId(user.getChatId())
                                        .name(user.getName())
                                        .build();
                                botEslatmaRepo.save(botEslatma);
                                if (user.getEmail() != null) {
                                    sendToEmail("Salom " + user.getName() + " sizning kiyim olish oyingiz keldi.Sizning kiyim olish vaqtingiz:" + date1 + "-kun" + month + "-oy," + year + "-yil", user.getEmail());
                                }
                            }
                        }
                        if ((clothe.getEslatishVaqti().getDate() == date.getDate()) && (clothe.getEslatishVaqti().getMonth() == date.getMonth())) {
                            Eslatma eslatma;
                            int date1 = clothe.getKiyimOlishvaqti().getDate();
                            int month = clothe.getKiyimOlishvaqti().getMonth() + 1;
                            int year = clothe.getKiyimOlishvaqti().getYear() + 1900;
                            eslatma = Eslatma.builder()
                                    .message(user.getName() + " ismli ishchining " + clothe.getName() + " nomli kiyim olish kuni kelmoqda.Ushbu ishchining kiyim olish vaqti: " + date1 + "-kun," + month + "-oy," + year + "-yil")
                                    .kun(date1)
                                    .oy(month)
                                    .year(date.getYear() + 1900)
                                    .userPhone(user.getPhoneNumber())
                                    .build();
                            eslatma.setName(user.getName());
                            if (!eslatmaRepo.existsEslatmaByMessage(eslatma.getMessage())) {
                                eslatmaRepo.save(eslatma);
                                BotEslatma botEslatma = BotEslatma.builder()
                                        .message("Salom sizning kiyim olish kuningiz kelmoqda.Sizning kiyim olish vaqtingiz:" + date1 + "-kun," + month + "-oy," + year + "-yil")
                                        .messageRus("Привет, приближается день сбора одежды. Время сбора одежды:" + date1 + "-день " + month + "-месяц " + year + "-год")
                                        .kiyimOlishVaqti(date)
                                        .kiyimNomi(clothe.getName())
                                        .chatId(user.getChatId())
                                        .name(user.getName())
                                        .build();
                                botEslatmaRepo.save(botEslatma);
                                if (user.getEmail() != null) {
                                    sendToEmail("Salom " + user.getName() + " sizning kiyim olish kuningiz kelmoqda.Sizning kiyim olish vaqtingiz:" + date1 + "-kun," + month + "-oy," + year + "-yil", user.getEmail());
                                }
                            }
                        }
                        if ((clothe.getKiyimOlishvaqti().getDate() == date.getDate()) && (clothe.getKiyimOlishvaqti().getMonth() == date.getMonth())) {
                            Eslatma eslatma;
                            int date1 = clothe.getKiyimOlishvaqti().getDate();
                            int month = clothe.getKiyimOlishvaqti().getMonth() + 1;
                            int year = clothe.getKiyimOlishvaqti().getYear() + 1900;
                            eslatma = Eslatma.builder()
                                    .message(user.getName() + " ismli ishchining " + clothe.getName() + " nomli kiyim olish kuni keldi.Ushbu ishchining  kiyim olish vaqti: " + date1 + "-kun," + month + "-oy," + year + "-yil")
                                    .kun(date.getDate())
                                    .oy(date.getMonth())
                                    .year(date.getYear() + 1900)
                                    .userPhone(user.getPhoneNumber())
                                    .build();
                            eslatma.setName(user.getName());
                            if (!eslatmaRepo.existsEslatmaByMessage(eslatma.getMessage())) {
                                eslatmaRepo.save(eslatma);
                                BotEslatma botEslatma = BotEslatma.builder()
                                        .message("Salom sizning kiyim olish kuningiz keldi.Sizning kiyim olish vaqtingiz:" + date1 + "-kun," + month + "-oy," + year + "-yil")
                                        .messageRus(
                                                "Здравствуйте, настал день вашей коллекции Ваше время коллекции:" + date1 + "-день," + month + "-месяц," + year + "-год")
                                        .kiyimOlishVaqti(date)
                                        .kiyimNomi(clothe.getName())
                                        .chatId(user.getChatId())
                                        .name(user.getName())
                                        .build();
                                botEslatmaRepo.save(botEslatma);
                                if (user.getEmail() != null) {
                                    sendToEmail("Salom " + user.getName() + " sizning kiyim olish kuningiz keldi.Sizning kiyim olish vaqtingiz:" + date1 + "-kun," + month + "-oy," + year + "-yil", user.getEmail());
                                }
                            }
                        }
                    }
                }
            }
        }
        return resWorkers;
    }

    public ResWorker getOneWorker(UUID uuid) {
        Optional<User> byId = userRepository.findById(uuid);
        List<ResClothe> clothes = new ArrayList<>();
        if (byId.isPresent()) {
            User user = byId.get();
            for (Clothe clothe : user.getClothe()) {
                ResClothe build = ResClothe.builder()
                        .giveDay(clothe.getKiyimOlishvaqti().getDate())
                        .giveMonth(clothe.getKiyimOlishvaqti().getMonth() + 1)
                        .giveYear(clothe.getKiyimOlishvaqti().getYear() + 1900)
                        .awaredDay(clothe.getEslatishVaqti().getDate())
                        .awaredMonth(clothe.getEslatishVaqti().getMonth() + 1)
                        .awaredYear(clothe.getEslatishVaqti().getYear() + 1900)
                        .photoId(clothe.getPhotoId())
                        .id(clothe.getId())
                        .name(clothe.getName())
                        .build();
                clothes.add(build);
            }
            return ResWorker.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .chatId(user.getChatId())
                    .clothe(clothes)
                    .build();
        }
        return null;
    }

    public ApiResponse addWorker(ReqWorker reqWorker) {
        Role role = roleRepo.findById(1).get();
        if (!userRepository.existsUserByPhoneNumberEqualsIgnoreCase(reqWorker.getPhoneNumber())) {
            User user;
            if (reqWorker.getEmail() == null) {
                user = User.builder()
                        .name(reqWorker.getName())
                        .lastName(reqWorker.getSurname())
                        .phoneNumber(reqWorker.getPhoneNumber())
                        .role(Collections.singletonList(role))
                        .build();
            }
            user = User.builder()
                    .name(reqWorker.getName())
                    .lastName(reqWorker.getSurname())
                    .phoneNumber(reqWorker.getPhoneNumber())
                    .email(reqWorker.getEmail())
                    .role(Collections.singletonList(role))
                    .build();
            userRepository.save(user);
            return new ApiResponse("ishchi qo'shildi", true);
        }
        return new ApiResponse("bunday telefon raqam bizning ma'lumotlar bazamizda mavjud", false);
    }

    public ApiResponse deleteWorker(UUID uuid) {
        Optional<User> byId = userRepository.findById(uuid);
        if (byId.isPresent()) {
            User user = byId.get();
            userRepository.delete(user);
            return new ApiResponse("ishchi olib tashlandi", true);
        }
        return new ApiResponse("bunday ishchi topilmadi", false);
    }

    public void sendToEmail(String text, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(text);
        simpleMailMessage.setSubject("ESLATMA !!!");
        simpleMailMessage.setFrom("alimirzaaliyevdev@gmail.com");
        simpleMailMessage.setTo(email);
        javaMailSender.send(simpleMailMessage);
    }

    public ApiResponse editWorker(UUID id, ReqWorker reqWorker) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setName(reqWorker.getName());
            user.setPhoneNumber(reqWorker.getPhoneNumber());
            user.setLastName(reqWorker.getSurname());
            user.setEmail(reqWorker.getEmail());
            userRepository.save(user);
            return new ApiResponse("Ishchi ma'lumotlari taxrirlandi", true);
        }
        return new ApiResponse("ushbu ishchi ma'lumotlar bazasida topilmadi", false);
    }


}
