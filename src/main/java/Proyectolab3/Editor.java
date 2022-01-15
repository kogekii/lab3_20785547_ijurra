package Proyectolab3;
import java.util.ArrayList;



public class Editor {
    private String name;
    private ArrayList<User> usuarios;
    private ArrayList<Docs> documentos;
    private User activo;
    private Fecha creacion;



    public void Crear_plataforma(String name, int day, int month, int year){
        this.name = name;
        this.usuarios = new ArrayList<>();
        this.documentos = new ArrayList<>();
        Fecha date = new Fecha();
        date.Crear_fecha(day, month, year);
        this.creacion = date;
    }

    public User getActivo() {
        return activo;
    }

    public boolean buscar_usuario(String name){
        for (int i = 0; i < this.usuarios.size() ; i++){
            if (name.equals(this.usuarios.get(i).GetUser())){
                return true;
            }
        }
        return false;
    }

    public boolean buscar_documento(int id){
        for (int i = 0; i < this.documentos.size() ; i++){
            if (id == this.documentos.get(i).GetID()){
                return true;
            }
        }
        return false;
    }

    public boolean verificar_credencial(String name, String pass){
        for (int i = 0; i < this.usuarios.size() ; i++){
            if (name.equals(this.usuarios.get(i).GetUser())){
                if (pass.equals(this.usuarios.get(i).GetPass())){
                    return true;
                }
            }
        }
        return false;
    }

    public User Retornar_usuario(String name){
        if (buscar_usuario(name)){
            for (int i = 0; i < this.usuarios.size() ; i++){
                if (name.equals(this.usuarios.get(i).GetUser())){
                    return this.usuarios.get(i);
                }
            }
        }
        return this.activo;
    }

    public Docs retornar_doc(int iddoc) {
        for (int i = 0; i < this.documentos.size(); i++){
            if (iddoc == this.documentos.get(i).GetID()){
                return this.documentos.get(i);
            }
        }
        return null;
    }

    public boolean Register(String name, String pass){
        if (!buscar_usuario(name)){
            User nuser = new User(this.usuarios.size(), name, pass);
            this.usuarios.add (nuser);
            return true;
        }
        return false;
    }

    public boolean Login(String name, String pass){
        if (verificar_credencial(name, pass)){
            this.activo = Retornar_usuario(name);
            return true;
        }
        return  false;
    }
    public void logout(){
        this.activo = null;
    }

    public void create(String namedoc, String contentdocs){
        Docs ndoc = new Docs(this.documentos.size(), namedoc, contentdocs, this.activo);
        this.documentos.add(ndoc);
        this.activo.ndocs(ndoc.GetID());
    }
    public boolean share(int iddoc, String usuario, String acces) {
        if (this.activo.verificar_permisos("any", iddoc, null)){
            if (acces.equals("W") || acces.equals("R") || acces.equals("C")) {
                if (buscar_usuario(usuario)) {
                    if (this.documentos.get(iddoc).agregarpermiso(usuario, acces)){
                        this.Retornar_usuario(usuario).ndocs(this.documentos.get(iddoc).GetID());
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return  false;
    }

    public boolean add (int iddoc, String newtext, Editor p){
        if (this.activo.verificar_permisos("W", iddoc, p)){
            String oldtext = this.documentos.get(iddoc).Gettextdoc();
            String New = oldtext + " " + newtext;
            this.documentos.get(iddoc).Settextdoc(New);
            return true;
        }
        return false;
    }

}