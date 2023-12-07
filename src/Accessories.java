public interface Accessories {
    double upstate();
    double Specialeffect( double max_hp);
    String getClassName();

    String getName();
}

class Rings implements Accessories{
    private double atk;
    private String name;

    public Rings(String name , double atk){
        this.name = name;
        this.atk = atk;

    }

    // return class
    public String getClassName() {
        return "Rings";
    }

    // return ชื่อ
    public String getName(){
        return  name;
    }

    // return status เพิ่ม
    public double upstate(){
        return atk;
    }

    //รับ max_hp มาคำนวน
    //return max_hp ที่คำนวนแล้ว
    public double Specialeffect(double max_hp){
        return (max_hp/100)*20;
    }
}

class Necklaces implements Accessories{
    private double s;
    private String name;

    public Necklaces(String name , double s){
        this.name = name;
        this.s = s;
    }


    // return status เพิ่ม
    public double upstate(){
        return s;
    }

    //รับ max_hp มาคำนวน
    //return max_hp ที่คำนวนแล้วเพิ่ม status ของ character
    public double Specialeffect( double max_hp){
        return (max_hp/100)*5;
    }

    // return class
    public String getClassName() {
        return "Necklaces";
    }

    // return ชื่อ
    public String getName(){
        return  name;
    }
}


//        1
//        64+16+4+1 -> 1010101.001 -> 1.010101001 * 2^6
//        E = 133 -> 10000101
//        1100001010101010010000000000000
//        kneel down and scream "ahhhhhhhhhh!!!"
