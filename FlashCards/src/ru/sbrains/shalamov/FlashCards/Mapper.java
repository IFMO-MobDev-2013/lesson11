package ru.sbrains.shalamov.FlashCards;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
    public static final String TAG = "FUCKENFUCK::Mapper ";
    private static final Map<String, Integer> WORDS = new HashMap<String, Integer>();

    static {
        WORDS.put("apple", R.drawable.apple);
        WORDS.put("apricot", R.drawable.apricot);
        WORDS.put("avocado", R.drawable.avocado);
        WORDS.put("banana", R.drawable.banana);
        WORDS.put("bilberry", R.drawable.bilberry);
        WORDS.put("blackberry", R.drawable.blackberry);
        WORDS.put("blackcurrant", R.drawable.blackcurrant);
        WORDS.put("blueberry", R.drawable.blueberry);
        WORDS.put("currant", R.drawable.currant);
        WORDS.put("cherry", R.drawable.cherry);
        WORDS.put("clementine", R.drawable.clementine);
        WORDS.put("cucumber", R.drawable.cucumber);
        WORDS.put("damson", R.drawable.damson);
        WORDS.put("eggplant", R.drawable.eggplant);
        WORDS.put("elderberry", R.drawable.elderberry);
        WORDS.put("grape", R.drawable.grape);
        WORDS.put("grapefruit", R.drawable.grapefruit);
        WORDS.put("artichoke", R.drawable.artichoke);
        WORDS.put("basil", R.drawable.basil);
        WORDS.put("aubergine", R.drawable.aubergine);
        WORDS.put("bean", R.drawable.bean);
        WORDS.put("broccoli", R.drawable.broccoli);
        WORDS.put("swede", R.drawable.swede);
        WORDS.put("peas", R.drawable.peas);
        WORDS.put("vegetable marrow", R.drawable.vegetable_marrow);
        WORDS.put("cabbage", R.drawable.cabbage);
        WORDS.put("potatoes", R.drawable.potatoes);
        WORDS.put("beer", R.drawable.beer);
        WORDS.put("bourbon whiskey", R.drawable.bourbon_whiskey);
        WORDS.put("champagne", R.drawable.champagne);
        WORDS.put("cocktail", R.drawable.cocktail);
        WORDS.put("eggnog", R.drawable.eggnog);
        WORDS.put("mulled wine", R.drawable.mulled_wine);
        WORDS.put("wine", R.drawable.wine);
        WORDS.put("scotch whiskey", R.drawable.scotch_whiskey);
        WORDS.put("wine cooler", R.drawable.wine_cooler);
        WORDS.put("vodka", R.drawable.vodka);
        WORDS.put("sake", R.drawable.sake);
        WORDS.put("schnapps", R.drawable.schnapps);
        WORDS.put("absinth", R.drawable.absinth);
        WORDS.put("house", R.drawable.house);
        WORDS.put("semi-detached house", R.drawable.semi_detached_house);
        WORDS.put("front door", R.drawable.front_door);
        WORDS.put("staircase", R.drawable.staircase);
        WORDS.put("roof", R.drawable.roof);
        WORDS.put("garden", R.drawable.garden);
        WORDS.put("garage", R.drawable.garage);
        WORDS.put("chimney", R.drawable.chimney);
        WORDS.put("aerial", R.drawable.aerial);
        WORDS.put("nursery", R.drawable.nursery);
        WORDS.put("bedroom", R.drawable.bedroom);
        WORDS.put("mirror", R.drawable.mirror);
        WORDS.put("comb", R.drawable.comb);
        WORDS.put("bast", R.drawable.bast);
        WORDS.put("bath", R.drawable.bath);
        WORDS.put("towel", R.drawable.towel);
        WORDS.put("soup", R.drawable.soup);
        WORDS.put("toothpaste", R.drawable.toothpaste);
        WORDS.put("shower", R.drawable.shower);
        WORDS.put("shampoo", R.drawable.shampoo);
        WORDS.put("foam bath", R.drawable.foam_bath);
        WORDS.put("sink", R.drawable.sink);
        WORDS.put("oven", R.drawable.oven);
        WORDS.put("knife", R.drawable.knife);
        WORDS.put("spoons", R.drawable.spoons);
        WORDS.put("teaspoons", R.drawable.teaspoons);
        WORDS.put("forks", R.drawable.forks);
        WORDS.put("napkins", R.drawable.napkins);
        WORDS.put("cups", R.drawable.cups);
        WORDS.put("kettle", R.drawable.kettle);
        WORDS.put("fridge", R.drawable.fridge);
        WORDS.put("to dice", R.drawable.to_dice);
        WORDS.put("amber", R.drawable.amber);
        WORDS.put("aqua", R.drawable.aqua);
        WORDS.put("aquamarine", R.drawable.aquamarine);
        WORDS.put("azure", R.drawable.azure);
        WORDS.put("bronze", R.drawable.bronze);
        WORDS.put("chocolate", R.drawable.chocolate);
        WORDS.put("emerald", R.drawable.emerald);
        WORDS.put("gold", R.drawable.gold);
        WORDS.put("khaki", R.drawable.khaki);
        WORDS.put("light green", R.drawable.light_green);
        WORDS.put("olive", R.drawable.olive);
        WORDS.put("orange", R.drawable.orange);
        WORDS.put("aerobics", R.drawable.aerobics);
        WORDS.put("an athlete", R.drawable.an_athlete);
        WORDS.put("archery", R.drawable.archery);
        WORDS.put("arrow", R.drawable.arrow);
        WORDS.put("badminton", R.drawable.badminton);
        WORDS.put("barbell", R.drawable.barbell);
        WORDS.put("baseball", R.drawable.baseball);
        WORDS.put("basketball", R.drawable.basketball);
        WORDS.put("volleyball", R.drawable.volleyball);
        WORDS.put("biathlon", R.drawable.biathlon);
        WORDS.put("billiards", R.drawable.billiards);
        WORDS.put("accordion", R.drawable.accordion);
        WORDS.put("bagpipe", R.drawable.bagpipe);
        WORDS.put("balalaika", R.drawable.balalaika);
        WORDS.put("ballet", R.drawable.ballet);
        WORDS.put("bass", R.drawable.bass);
        WORDS.put("basson", R.drawable.basson);
        WORDS.put("baton", R.drawable.baton);
        WORDS.put("bow", R.drawable.bow);
        WORDS.put("cello", R.drawable.cello);
        WORDS.put("clarinet", R.drawable.clarinet);
        WORDS.put("arch", R.drawable.arch);
        WORDS.put("auto dealer", R.drawable.auto_dealer);
        WORDS.put("car dealer", R.drawable.car_dealer);
        WORDS.put("bakery", R.drawable.bakery);
        WORDS.put("bank", R.drawable.bank);
        WORDS.put("bar", R.drawable.bar);
        WORDS.put("barber shop", R.drawable.barber_shop);
        WORDS.put("bench", R.drawable.bench);
        WORDS.put("seat", R.drawable.seat);
        WORDS.put("bridge", R.drawable.bridge);
        WORDS.put("buildings", R.drawable.buildings);
        WORDS.put("circus", R.drawable.circus);
    }


    public static int[] map(String[] en) {

        int[] result = new int[en.length];
        try {
            for (int i = 0; i < en.length; ++i) {
                result[i] = WORDS.get(en[i]);
//        switch (en[i])
//        {
//            case "apple":
//                result[i] = R.drawable.apple;
//                break;
//
//            case "apricot":
//                result[i] = R.drawable.apricot;
//                break;
//
//            case "avocado":
//                result[i] = R.drawable.avocado;
//                break;
//
//            case "banana":
//                result[i] = R.drawable.banana;
//                break;
//
//            case "bilberry":
//                result[i] = R.drawable.bilberry;
//                break;
//
//            case "blackberry":
//                result[i] = R.drawable.blackberry;
//                break;
//
//            case "blackcurrant":
//                result[i] = R.drawable.blackcurrant;
//                break;
//
//            case "blueberry":
//                result[i] = R.drawable.blueberry;
//                break;
//
//            case "currant":
//                result[i] = R.drawable.currant;
//                break;
//
//            case "cherry":
//                result[i] = R.drawable.cherry;
//                break;
//
//            case "clementine":
//                result[i] = R.drawable.clementine;
//                break;
//
//            case "cucumber":
//                result[i] = R.drawable.cucumber;
//                break;
//
//            case "damson":
//                result[i] = R.drawable.damson;
//                break;
//
//            case "eggplant":
//                result[i] = R.drawable.eggplant;
//                break;
//
//            case "elderberry":
//                result[i] = R.drawable.elderberry;
//                break;
//
//            case "grape":
//                result[i] = R.drawable.grape;
//                break;
//
//            case "grapefruit":
//                result[i] = R.drawable.grapefruit;
//                break;
//
//            case "Artichoke":
//                result[i] = R.drawable.Artichoke;
//                break;
//
//            case "Basil":
//                result[i] = R.drawable.Basil;
//                break;
//
//            case "Aubergine":
//                result[i] = R.drawable.Aubergine;
//                break;
//
//            case "Bean":
//                result[i] = R.drawable.Bean;
//                break;
//
//            case "Broccoli":
//                result[i] = R.drawable.Broccoli;
//                break;
//
//            case "Swede":
//                result[i] = R.drawable.Swede;
//                break;
//
//            case "Peas":
//                result[i] = R.drawable.Peas;
//                break;
//
//            case "Vegetable_marrow":
//                result[i] = R.drawable.Vegetable_marrow;
//                break;
//
//            case "Cabbage":
//                result[i] = R.drawable.Cabbage;
//                break;
//
//            case "Potatoes":
//                result[i] = R.drawable.Potatoes;
//                break;
//
//            case "beer":
//                result[i] = R.drawable.beer;
//                break;
//
//            case "bourbon_whiskey":
//                result[i] = R.drawable.bourbon_whiskey;
//                break;
//
//            case "champagne":
//                result[i] = R.drawable.champagne;
//                break;
//
//            case "cocktail":
//                result[i] = R.drawable.cocktail;
//                break;
//
//            case "eggnog":
//                result[i] = R.drawable.eggnog;
//                break;
//
//            case "mulled_wine":
//                result[i] = R.drawable.mulled_wine;
//                break;
//
//            case "wine":
//                result[i] = R.drawable.wine;
//                break;
//
//            case "scotch_whiskey":
//                result[i] = R.drawable.scotch_whiskey;
//                break;
//
//            case "wine_cooler":
//                result[i] = R.drawable.wine_cooler;
//                break;
//
//            case "vodka":
//                result[i] = R.drawable.vodka;
//                break;
//
//            case "sake":
//                result[i] = R.drawable.sake;
//                break;
//
//            case "schnapps":
//                result[i] = R.drawable.schnapps;
//                break;
//
//            case "absinth":
//                result[i] = R.drawable.absinth;
//                break;
//
//            case "House":
//                result[i] = R.drawable.House;
//                break;
//
//            case "Semi-detached_house":
//                result[i] = R.drawable.Semi_detached_house;
//                break;
//
//            case "Front_door":
//                result[i] = R.drawable.Front_door;
//                break;
//
//            case "Staircase":
//                result[i] = R.drawable.Staircase;
//                break;
//
//            case "Roof":
//                result[i] = R.drawable.Roof;
//                break;
//
//            case "Garden":
//                result[i] = R.drawable.Garden;
//                break;
//
//            case "Garage":
//                result[i] = R.drawable.Garage;
//                break;
//
//            case "Chimney":
//                result[i] = R.drawable.Chimney;
//                break;
//
//            case "Aerial":
//                result[i] = R.drawable.Aerial;
//                break;
//
//            case "Nursery":
//                result[i] = R.drawable.Nursery;
//                break;
//
//            case "Bedroom":
//                result[i] = R.drawable.Bedroom;
//                break;
//
//            case "Mirror":
//                result[i] = R.drawable.Mirror;
//                break;
//
//            case "Comb":
//                result[i] = R.drawable.Comb;
//                break;
//
//            case "Bast":
//                result[i] = R.drawable.Bast;
//                break;
//
//            case "Bath":
//                result[i] = R.drawable.Bath;
//                break;
//
//            case "Towel":
//                result[i] = R.drawable.Towel;
//                break;
//
//            case "Soup":
//                result[i] = R.drawable.Soup;
//                break;
//
//            case "Toothpaste":
//                result[i] = R.drawable.Toothpaste;
//                break;
//
//            case "Shower":
//                result[i] = R.drawable.Shower;
//                break;
//
//            case "Shampoo":
//                result[i] = R.drawable.Shampoo;
//                break;
//
//            case "Foam_bath":
//                result[i] = R.drawable.Foam_bath;
//                break;
//
//            case "Sink":
//                result[i] = R.drawable.Sink;
//                break;
//
//            case "Oven":
//                result[i] = R.drawable.Oven;
//                break;
//
//            case "Knife":
//                result[i] = R.drawable.Knife;
//                break;
//
//            case "Spoons":
//                result[i] = R.drawable.Spoons;
//                break;
//
//            case "Teaspoons":
//                result[i] = R.drawable.Teaspoons;
//                break;
//
//            case "Forks":
//                result[i] = R.drawable.Forks;
//                break;
//
//            case "Napkins":
//                result[i] = R.drawable.Napkins;
//                break;
//
//            case "Cups":
//                result[i] = R.drawable.Cups;
//                break;
//
//            case "Kettle":
//                result[i] = R.drawable.Kettle;
//                break;
//
//            case "Fridge":
//                result[i] = R.drawable.Fridge;
//                break;
//
//            case "To_dice":
//                result[i] = R.drawable.To_dice;
//                break;
//
//            case "Amber":
//                result[i] = R.drawable.Amber;
//                break;
//
//            case "Aqua":
//                result[i] = R.drawable.Aqua;
//                break;
//
//            case "Aquamarine":
//                result[i] = R.drawable.Aquamarine;
//                break;
//
//            case "Azure":
//                result[i] = R.drawable.Azure;
//                break;
//
//            case "Bronze":
//                result[i] = R.drawable.Bronze;
//                break;
//
//            case "Chocolate":
//                result[i] = R.drawable.Chocolate;
//                break;
//
//            case "Emerald":
//                result[i] = R.drawable.Emerald;
//                break;
//
//            case "Gold":
//                result[i] = R.drawable.Gold;
//                break;
//
//            case "Khaki":
//                result[i] = R.drawable.Khaki;
//                break;
//
//            case "Light_green":
//                result[i] = R.drawable.Light_green;
//                break;
//
//            case "Olive":
//                result[i] = R.drawable.Olive;
//                break;
//
//            case "Orange":
//                result[i] = R.drawable.Orange;
//                break;
//
//            case "Aerobics":
//                result[i] = R.drawable.Aerobics;
//                break;
//
//            case "An_athlete":
//                result[i] = R.drawable.An_athlete;
//                break;
//
//            case "Archery":
//                result[i] = R.drawable.Archery;
//                break;
//
//            case "Arrow":
//                result[i] = R.drawable.Arrow;
//                break;
//
//            case "Badminton":
//                result[i] = R.drawable.Badminton;
//                break;
//
//            case "Barbell":
//                result[i] = R.drawable.Barbell;
//                break;
//
//            case "Baseball":
//                result[i] = R.drawable.Baseball;
//                break;
//
//            case "Basketball":
//                result[i] = R.drawable.Basketball;
//                break;
//
//            case "volleyball":
//                result[i] = R.drawable.volleyball;
//                break;
//
//            case "Biathlon":
//                result[i] = R.drawable.Biathlon;
//                break;
//
//            case "Billiards":
//                result[i] = R.drawable.Billiards;
//                break;
//
//            case "Accordion":
//                result[i] = R.drawable.Accordion;
//                break;
//
//            case "Bagpipe":
//                result[i] = R.drawable.Bagpipe;
//                break;
//
//            case "Balalaika":
//                result[i] = R.drawable.Balalaika;
//                break;
//
//            case "Ballet":
//                result[i] = R.drawable.Ballet;
//                break;
//
//            case "Bass":
//                result[i] = R.drawable.Bass;
//                break;
//
//            case "Basson":
//                result[i] = R.drawable.Basson;
//                break;
//
//            case "Baton":
//                result[i] = R.drawable.Baton;
//                break;
//
//            case "Bow":
//                result[i] = R.drawable.Bow;
//                break;
//
//            case "Cello":
//                result[i] = R.drawable.Cello;
//                break;
//
//            case "Clarinet":
//                result[i] = R.drawable.Clarinet;
//                break;
//
//            case "Arch":
//                result[i] = R.drawable.Arch;
//                break;
//
//            case "Auto_dealer":
//                result[i] = R.drawable.Auto_dealer;
//                break;
//
//            case "car_dealer":
//                result[i] = R.drawable.car_dealer;
//                break;
//
//            case "Bakery":
//                result[i] = R.drawable.Bakery;
//                break;
//
//            case "Bank":
//                result[i] = R.drawable.Bank;
//                break;
//
//            case "Bar":
//                result[i] = R.drawable.Bar;
//                break;
//
//            case "Barber_shop":
//                result[i] = R.drawable.Barber_shop;
//                break;
//
//            case "Bench":
//                result[i] = R.drawable.Bench;
//                break;
//
//            case "seat":
//                result[i] = R.drawable.seat;
//                break;
//
//            case "Bridge":
//                result[i] = R.drawable.Bridge;
//                break;
//
//            case "Buildings":
//                result[i] = R.drawable.Buildings;
//                break;
//
//            case "Circus":
//                result[i] = R.drawable.Circus;
//                break;
//        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
