package ali.project.railway_project.Service;

import ali.project.railway_project.Entity.Clothe;
import ali.project.railway_project.Entity.Eslatma;
import ali.project.railway_project.Entity.User;
import ali.project.railway_project.Payload.ApiResponse;
import ali.project.railway_project.Payload.ReqClothe;
import ali.project.railway_project.Payload.ResClothe;
import ali.project.railway_project.Repository.ClotheRepo;
import ali.project.railway_project.Repository.EslatmaRepo;
import ali.project.railway_project.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClotheService {

    private final UserRepository userRepository;

    private final ClotheRepo clotheRepo;

    public ApiResponse deleteClothe(UUID uuid, Integer id){
        User user = userRepository.findById(uuid).get();
        Clothe clothe = clotheRepo.findById(id).get();
        user.getClothe().remove(clothe);
        userRepository.save(user);
        clotheRepo.delete(clothe);
        return new ApiResponse("kiyim o'chirildi", true);
    }

    public ApiResponse addClothe(UUID id, ReqClothe reqClothe) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            Clothe build = Clothe.builder()
                    .name(reqClothe.getName())
                    .kiyimOlishvaqti(reqClothe.getOlishVaqti())
                    .eslatishVaqti(reqClothe.getEslatishVaqti())
                    .build();
            Clothe save = clotheRepo.save(build);
            user.getClothe().add(save);
            userRepository.save(user);
            return new ApiResponse("ushbu ishchi uchun kiyim saqlandi", true);
        }
        return new ApiResponse("bunday ishchi topilmadi", false);
    }

    public List<ResClothe> getClothe(UUID uuid) {
        List<ResClothe> resClothes = new ArrayList<>();
        Optional<User> byId = userRepository.findById(uuid);
        if (byId.isPresent()) {
            User user = byId.get();
            for (Clothe clothe : user.getClothe()) {
                ResClothe build = ResClothe.builder()
                        .giveDay(clothe.getKiyimOlishvaqti().getDate())
                        .giveMonth(clothe.getKiyimOlishvaqti().getMonth())
                        .giveYear(clothe.getKiyimOlishvaqti().getYear())
                        .awaredDay(clothe.getEslatishVaqti().getDate())
                        .awaredMonth(clothe.getEslatishVaqti().getMonth())
                        .awaredYear(clothe.getEslatishVaqti().getMonth())
                        .photoId(clothe.getPhotoId())
                        .id(clothe.getId())
                        .build();
                resClothes.add(build);
            }
        }
        return resClothes;
    }

    public ApiResponse editClothe(UUID uuid, Integer id, ReqClothe reqClothe){
        User user = userRepository.findById(uuid).get();
        for (Clothe clothe : user.getClothe()) {
            if (clothe.getId().equals(id)){
                if (reqClothe.getName().trim().length() != 0){
                    clothe.setName(reqClothe.getName());
                }
                if (reqClothe.getEslatishVaqti() != null) {
                    clothe.setEslatishVaqti(reqClothe.getEslatishVaqti());
                }
                if (reqClothe.getOlishVaqti() != null) {
                    clothe.setKiyimOlishvaqti(reqClothe.getOlishVaqti());
                }
                userRepository.save(user);
                clotheRepo.save(clothe);
                return new ApiResponse("kiyim taxrirlandi", true);
            }
        }
        return new ApiResponse("Ma'lumotlar bazasida hatolik", false);
    }
}
