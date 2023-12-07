public interface Character {
    void showstate();

    void attack(character_ p);

    void level_up();

    void takedamage(double d);

}

interface skill_warrior{
    void skill_Buffatk();
    void skill_Buffdef();

}

interface skill_wizard {
    void skill_Fireball(character_ p);
    void skill_Debuff(character_ p);
}

abstract class character_ implements Character{
    protected String name;
    protected int level;
    protected double max_hp,hp,max_mana,mana,damage,defense,max_speed;

    protected double d,s,r;

    protected boolean DBdef = false;

    protected Accessories accessories;



    public character_(String name,int level,double d , double s, double r){
        this.name = name;
        this.level = level;
        this.d = d ;
        this.s = s ;
        this.r = r;
        accessories = null;


    }

    /*
    * รับ Accessories เข้ามา
    * ให้ accessories ของ character เป็นอันนั้น
    * */
    public void equipAccessory(Accessories accessory) {
        accessories = accessory;
    }

    public void level_up(){
        level++;
    }

    /*
    * โจมตีโดยเลือก character
    * character นั้นจะได้รับ damage ไป
    * */
    public void attack(character_ p){
        System.out.println("|---------------------------------------|");
        System.out.println("    " + name + " attack to " + p.name);
        System.out.println("|---------------------------------------|");
        System.out.println("");
        p.takedamage(damage);
    }


     /*รับdamage จากการโจมตี
     * โดยจะเอา defense - damage
     * เมื่อเลือดต่ำกว่า 0 ก็จะกลายเป็น 0
     * */
    public void takedamage(double d){
        double da = defense - d;
        if(da > 0){
            da = 0;
        }

        hp += da;
        if(hp  < 0){
            hp = 0;
        }
    }


    /*
    * โชว์ Status ของ character
    * */
    public void showstate(){
        update_state();
        System.out.println("|---------------------------------------|");
        System.out.println("    Name = " + name + " " + getClass());
        System.out.println("    Level = " + level);
        System.out.println("    Hp = " + hp + " / " + max_hp);
        System.out.println("    Mana = " + mana + " / " + max_mana);
        System.out.println("    Speed = " + max_speed);
        System.out.println("    Damage = " + damage);
        System.out.println("    Defense = " + defense);
        if(accessories != null) {
            System.out.println("    Accessory = " + accessories.getName());
        }
        System.out.println("|---------------------------------------|");
        System.out.println(" ");
    }

    public abstract void update_state();

}
class Wizard extends character_ implements skill_wizard{

    public Wizard(String name, int level, double d, double s, double r) {
        super(name, level, d, s, r);
        hp = 100+10*(level-1);
        mana = 50+10*(level-1);
    }

    /*
    * เปลี่ยนค่า status ตาม level และของสวมใส่
    * ถ้า DBdef เป็น ture defense ลด
    * */
    public void update_state(){
        max_hp = 100+10*(level-1);
        max_mana = 50+10*(level-1);
        max_speed =  r + (r * (0.1+0.03*(level-1)));
        damage = d * (1 + 0.1 * (level - 1));
        defense = s * (1 + 0.05 * (level - 1));
        if(DBdef == true){
            defense *= 0.75;
        }
        if(accessories != null){
            if(accessories.getClassName().equals("Rings")) {
                damage += accessories.upstate();
                if (hp < (max_hp / 100) * 30) {
                    hp += accessories.Specialeffect(max_hp);
                }
            }
            if(accessories.getClassName().equals("Necklaces")){
                defense += accessories.upstate();
                if(hp < (max_hp/100)*20){
                    damage+=accessories.Specialeffect(max_hp);
                }
            }
        }

    }

    /*
    * เป็น skill เอาไว้โจมตี โดยเลือก character ที่จะโจมตี
    * ลด mana ลงไป ถ้าหากมานาไม่พอ skill จะไม่มีผล
    * character ที่ถูกเลือกจะ takedamage
    * */
    public void skill_Fireball(character_ p){
        mana -= 40;
        if(mana < 0){
            mana += 40;
            return ;
        }
        double da = max_mana * 1.5;
        System.out.println("|---------------------------------------|");
        System.out.println("    " + name + " Fireball to " + p.name);
        System.out.println("|---------------------------------------|");
        System.out.println("");
        p.takedamage(da);
    }

    /*
     * เป็น skill เลือก character เพื่อลด status
     * ลด mana ลงไป ถ้าหากมานาไม่พอ skill จะไม่มีผล
     * character ที่ถูกเลือก ค่า DBdef เป็น ture
     * */
    public void skill_Debuff(character_ p){
        mana -= 20;
        if(mana < 0){
            mana += 20;
            return ;
        }
        System.out.println("|---------------------------------------|");
        System.out.println("    " + name + " Debuff_def to " + p.name);
        System.out.println("|---------------------------------------|");
        System.out.println("");
        p.DBdef = true;
    }

}


class Warrior extends character_ implements skill_warrior {


    private boolean Batk = false, Bdef = false ;//มีตัวแปรเพิ่มขึ้นมาเพื่อจำว่าใช้ skill รึป่าว
    public Warrior(String name, int level, double d, double s, double r) {
        super(name, level, d, s, r);
        hp = 100+20*(level-1);
        mana = 50+2*(level-1);
    }


    /*
     * โจมตีโดยเลือก character
     * character นั้นจะได้รับ damage ไป
     * Batk เป็น false
     * */
    public void attack(character_ p){
        System.out.println("|---------------------------------------|");
        System.out.println("    " + name + " attack to " + p.name);
        System.out.println("|---------------------------------------|");
        System.out.println("");
        p.takedamage(damage);
        Batk = false;
    }

    /*รับdamage จากการโจมตี
     * โดยจะเอา defense - damage
     * เมื่อเลือดต่ำกว่า 0 ก็จะกลายเป็น 0
     * เปลี่นน ฺBdef เป้น false
     * */
    public void takedamage(double d){
        double da = defense - d;
        if(da > 0){
            da = 0;
        }

        hp += da;
        if(hp  < 0){
            hp = 0;
        }
        Bdef = false;
    }


    /*
     * เปลี่ยนค่า status ตาม level และของสวมใส่
     * DBdef ture -> ลด defense
     * Bdef ture -> เพิ่ม defense
     * Batk ture -> เพิ่ม defense
     * */
    public void update_state(){
        max_hp = 100+20*(level-1);
        max_mana = 50+2*(level-1);
        max_speed =  r + (r * (0.1+0.05*(level-1)));
        damage = d * (1 + 0.2 * (level - 1));
        if(Batk == true){damage *= 1.5;}
        defense = s * (1 + 0.15 * (level - 1));
        if(Bdef == true){defense = (s * (1 + 0.15 * (level - 1)))*1.5;}
        if(DBdef == true){defense *= 0.75;}
        if(accessories != null){
            if(accessories.getClassName().equals("Rings")) {
                damage += accessories.upstate();
                if (hp < (max_hp / 100) * 30) {
                    hp += accessories.Specialeffect(max_hp);
                }
            }
            if(accessories.getClassName().equals("Necklaces")){
                defense += accessories.upstate();
                if(hp < (max_hp/100)*20){
                    damage+=accessories.Specialeffect(max_hp);
                }
            }
        }
    }

    /*
     * ลด mana ลงไป ถ้าหากมานาไม่พอ skill จะไม่มีผล
     * ฺBatk -> ture
     * */
    public void skill_Buffatk(){
        mana -= 20;
        if(mana < 0){
            mana += 20;
            return ;
        }
        System.out.println("|---------------------------------------|");
        System.out.println("    " + name + " buff_atk ");
        System.out.println("|---------------------------------------|");
        System.out.println("");
        Batk = true;
    }

    /*
     * ลด mana ลงไป ถ้าหากมานาไม่พอ skill จะไม่มีผล
     * Bdef -> ture
     * */
    public void skill_Buffdef(){
        mana -= 15;
        if(mana < 0){
            mana += 15;
            return;
        }
        System.out.println("|---------------------------------------|");
        System.out.println("    " + name + " buff_def ");
        System.out.println("|---------------------------------------|");
        System.out.println("");
        Bdef = true;
    }
}
