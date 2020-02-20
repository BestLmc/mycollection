package main.java.cn.lmc.collection.javabasic.java8.base;

import lombok.Data;
import org.junit.Test;

import java.util.Optional;

/**
 * OptionalTestExpl
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class OptionalTestExpl {
    /**
     * 电脑里【有可能】有声卡
     * 声卡【有可能】有USB接口
     */
    @Test
    public void optionalTest() {
        // 没有声卡，没有 Usb 的电脑
        Computer computerNoUsb = new Computer();
        computerNoUsb.setSoundCard(Optional.empty());
        // 获取 usb 版本
        Optional<Computer> computerOptional = Optional.ofNullable(computerNoUsb);
        String version = computerOptional.flatMap(Computer::getSoundCard).flatMap(SoundCard::getUsb)
                .map(Usb::getVersion).orElse("UNKNOWN");
        System.out.println(version);
        System.out.println("-----------------");

        // 如果有值，则输出
        SoundCard soundCard = new SoundCard();
        Usb usb = new Usb();
        usb.setVersion("2.0");
        soundCard.setUsb(Optional.ofNullable(usb));
        Optional<SoundCard> optionalSoundCard = Optional.ofNullable(soundCard);
        optionalSoundCard.ifPresent(System.out::println);
        // 如果有值，则输出
        if (optionalSoundCard.isPresent()) {
            System.out.println(optionalSoundCard.get());
        }

        // 输出没有值，则没有输出
        Optional<SoundCard> optionalSoundCardEmpty = Optional.ofNullable(null);
        optionalSoundCardEmpty.ifPresent(System.out::println);
        System.out.println("-----------------");

        // 筛选 Usb2.0
        optionalSoundCard.map(SoundCard::getUsb)
                .filter(usb1 -> "3.0".equals(usb1.map(Usb::getVersion)
                        .orElse("UBKNOW")))
                .ifPresent(System.out::println);
    }
}

/**
 * 计算机
 */
//@Data
class Computer {
    private Optional<SoundCard> soundCard;

    public Optional<SoundCard> getSoundCard() {
        return soundCard;
    }

    public void setSoundCard(Optional<SoundCard> soundCard) {
        this.soundCard = soundCard;
    }
}

/**
 * 声卡
 */
//@Data
class SoundCard {
    private Optional<Usb> usb;

    public Optional<Usb> getUsb() {
        return usb;
    }

    public void setUsb(Optional<Usb> usb) {
        this.usb = usb;
    }
}

/**
 * USB
 */
//@Data
class Usb {
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}