package italogvss.digitalshowcase.Util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import italogvss.digitalshowcase.model.PriceTable;
import italogvss.digitalshowcase.model.Product;
import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.model.UserRole;
import italogvss.digitalshowcase.repositories.PriceTableRepository;
import italogvss.digitalshowcase.repositories.ProductRepository;
import italogvss.digitalshowcase.repositories.UserRepository;

@Component
@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PriceTableRepository priceTableRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BCryptPasswordEncoder bce;

    @Bean
    public CommandLineRunner initializeAdminUser() {
        return args -> {
            // Verifica e cria o usuário admin
            Long adminId = createUserIfNotExists("admin", "Administrator", "12345", UserRole.ADMIN, 10, 5, true, null);

            // Recupera o usuário admin para definir como owner dos outros usuários
            User admin = repository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));
            
            // Verifica se já existe uma PriceTable para o admin
            List<PriceTable> adminPriceTables = priceTableRepository.findByUserId(admin.getId());
            if (adminPriceTables == null || adminPriceTables.isEmpty()) {
                // Cria a PriceTable
                PriceTable pt = new PriceTable();
                pt.setName("Tabela Padrão");
                pt.setUser(admin);
                pt = priceTableRepository.save(pt);
                
                // Cria 50 produtos fictícios associados à PriceTable
                List<Product> products = new ArrayList<>();
                for (int i = 1; i <= 50; i++) {
                    Product product = new Product();
                    product.setName("Produto " + i);
                    product.setPrice(10.0f); // Define um preço padrão
                    product.setUser(admin);
                    product.setPricetable(pt);
                    products.add(product);
                }
                productRepository.saveAll(products);
                System.out.println("PriceTable e 50 produtos criados para o admin.");
            } else {
                System.out.println("PriceTable já existe para o admin. Nenhuma ação necessária.");
            }
        };
    }

    private Long createUserIfNotExists(String login, String name, String password, UserRole role,
                                         int maxPlaylists, int maxTables, boolean isActive, User owner) {
        // Verifica se o usuário já existe
        if (repository.findByUsername(login) == null) {
            User newUser = new User();
            newUser.setUsername(login);
            newUser.setName(name);
            newUser.setPassword(bce.encode(password));
            newUser.setRole(role);
            newUser.setIsActive(isActive);
            newUser.setOwner(owner);
                    
            // Salva e retorna o ID do usuário
            return repository.save(newUser).getId();
        } else {
            System.out.println("Usuário com login " + login + " já existe. Não será criado novamente.");
            return repository.findAll().stream()
                .filter(user -> user.getUsername().equals(login))
                .findFirst()
                .map(User::getId)
                .orElse(null);
        }
    }
}