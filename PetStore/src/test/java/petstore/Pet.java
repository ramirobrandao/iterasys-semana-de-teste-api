package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {

        String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade pet

        public String lerJson(String caminhoJson) throws IOException{

            return new String (Files.readAllBytes(Paths.get(caminhoJson)));
    }

        //POST
        @Test(priority = 1)
        public void incluirPet() throws IOException {
            String jsonBody = lerJson("db/pet1.json");

            given()   //dado
                     .contentType("application/json") //comum em API REST
                     .log().all()
                     .body(jsonBody)
            .when()   //quando
                     .post(uri)
            .then()  //entao
                     .log().all()
                     .statusCode(200)
                     .body("name", is("Snoopy"))
                     .body("status", is("available"))
                     .body("category.name", is ("dog"))
                     .body("tags.name", contains("sta"))
            ;
        }

        //GET
        @Test(priority = 2)
        public void consultarId() {
            String petId = "1908202129";

            given() //dado
                    .contentType("application/json")
                    .log().all()
            .when() //quando
                    .get(uri + "/" + petId)
            .then() //entao
                    .log().all()
                    .statusCode(200)
                    .body("name", is("Snoopy"))
                    .body("status", is("available"))
                    .body("category.name", is ("dog"))
                    .body("tags.name", contains("sta"))
            ;
        }

        //PUT
        @Test(priority = 3)
        public void alterarPet() throws IOException {
            String jsonBody = lerJson("db/pet2.json");

            given() //dado
                    .contentType("application/json")
                    .log().all()
                    .body(jsonBody)

            .when() //quando
                    .put(uri)
            .then() //entao
                    .log().all()
                    .statusCode(200)
                    .body("name", is("Snoopy"))
                    .body("status", is("sold"))

            ;
        }

        //DELETE
            @Test(priority = 4)
            public void excluirPet() {
                String petId = "1908202129";

                given() //dado
                        .contentType("application/json")
                        .log().all()
                .when() //quando
                        .delete(uri + "/" + petId)
                .then() //entao
                        .log().all()
                        .statusCode(200)
                        .body("code", is(200))
                        .body("type", is("unknown"))
                        .body("message", is("1908202129"))
                ;
            }

  }