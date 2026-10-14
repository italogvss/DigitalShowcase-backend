# Digital Showcase — Backend

API do **Digital Showcase**, um sistema de sinalização digital (TV corporativa / menu board) para exibir tabelas de preços, imagens e vídeos em telas e TV boxes.

Pelo painel, o usuário cadastra produtos e tabelas de preço, envia mídias, monta layouts e playlists e vincula cada tela (dispositivo) a uma playlist por meio de um código. As telas recebem as atualizações em tempo real via WebSocket.

**Principais recursos**

- Autenticação com JWT e perfis de acesso (Admin, Manager, User)
- CRUD de produtos, tabelas de preço e layouts de tabela
- Upload de imagens e vídeos com geração automática de miniaturas
- Playlists com templates e envio de atualizações em tempo real para os dispositivos
- Gerenciamento de sessões de dispositivos (TVs) e informações do sistema

## Como iniciar

**Pré-requisitos:** Java 21 e MySQL.

1. Crie o banco de dados no MySQL:

   ```sql
   CREATE DATABASE projecttv2;
   ```

2. Ajuste a conexão em `src/main/resources/application-dev.properties` (URL, usuário e senha do banco).

3. Rode a aplicação:

   ```bash
   ./mvnw spring-boot:run        # Linux/macOS
   mvnw.cmd spring-boot:run      # Windows
   ```

A API sobe em `http://localhost:8080` e o WebSocket fica em `/devws`. As tabelas são criadas automaticamente e, na primeira execução, é criado o usuário `admin` / `12345` com uma tabela de preços de exemplo.

Variáveis de ambiente opcionais:

| Variável | Padrão |
| --- | --- |
| `JWT_SECRET` | `my-secret-key` |
| `MEDIA_IMAGE_DIR` | `media/images` |
| `MEDIA_VIDEO_DIR` | `media/video` |

## Stack

- **Java 21** + **Spring Boot 3.3** (Web, Data JPA, Security, Validation, WebSocket/STOMP)
- **MySQL** com Hibernate
- **JWT** (auth0 java-jwt)
- **MapStruct** para mapeamento de DTOs
- **Thumbnailator** e **JCodec** para miniaturas de imagens e vídeos
- **OSHI** para informações do sistema
- **Maven**
