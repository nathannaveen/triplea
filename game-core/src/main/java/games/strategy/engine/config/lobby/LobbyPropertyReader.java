package games.strategy.engine.config.lobby;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.Optional;

import com.google.common.annotations.VisibleForTesting;

import games.strategy.engine.config.FilePropertyReader;
import games.strategy.engine.config.PropertyReader;

/**
 * Reads property values from the lobby configuration file.
 */
public final class LobbyPropertyReader {
  private static final String LOBBY_PROPERTIES_FILE_PATH_1 = "config/lobby/lobby.properties";
  private static final String LOBBY_PROPERTIES_FILE_PATH_2 = "lobby.properties";

  private final PropertyReader propertyReader;

  public LobbyPropertyReader() {
    this(getReader());
  }

  private static PropertyReader getReader() {
    // fall back to a secondary path if primary is not available (to help support development)
    return readPropertyFile(LOBBY_PROPERTIES_FILE_PATH_1)
        .orElseGet(() -> readPropertyFile(LOBBY_PROPERTIES_FILE_PATH_2)
            .orElseThrow(() -> new IllegalStateException(String.format(
                "Could not find property file at either: %s, or %s",
                new File(LOBBY_PROPERTIES_FILE_PATH_1).getAbsolutePath(),
                new File(LOBBY_PROPERTIES_FILE_PATH_2).getAbsolutePath()))));
  }

  private static Optional<PropertyReader> readPropertyFile(String path) {
    if (!new File(path).exists()) {
      return Optional.empty();
    }
    return Optional.of(new FilePropertyReader(path));
  }

  @VisibleForTesting
  public LobbyPropertyReader(final PropertyReader propertyReader) {
    checkNotNull(propertyReader);

    this.propertyReader = propertyReader;
  }

  public int getPort() {
    return propertyReader.readIntegerPropertyOrDefault(PropertyKeys.PORT, DefaultValues.PORT);
  }

  public String getPostgresDatabase() {
    return propertyReader.readPropertyOrDefault(PropertyKeys.POSTGRES_DATABASE, DefaultValues.POSTGRES_DATABASE);
  }

  public String getPostgresHost() {
    return propertyReader.readPropertyOrDefault(PropertyKeys.POSTGRES_HOST, DefaultValues.POSTGRES_HOST);
  }

  public String getPostgresPassword() {
    return propertyReader.readProperty(PropertyKeys.POSTGRES_PASSWORD);
  }

  public int getPostgresPort() {
    return propertyReader.readIntegerPropertyOrDefault(PropertyKeys.POSTGRES_PORT, DefaultValues.POSTGRES_PORT);
  }

  public String getPostgresUser() {
    return propertyReader.readProperty(PropertyKeys.POSTGRES_USER);
  }

  public boolean isMaintenanceMode() {
    return propertyReader.readBooleanPropertyOrDefault(PropertyKeys.MAINTENANCE_MODE, DefaultValues.MAINTENANCE_MODE);
  }

  /**
   * The valid lobby property keys.
   */
  @VisibleForTesting
  public interface PropertyKeys {
    String MAINTENANCE_MODE = "maintenance_mode";
    String PORT = "port";
    String POSTGRES_DATABASE = "postgres_database";
    String POSTGRES_HOST = "postgres_host";
    String POSTGRES_PASSWORD = "postgres_password";
    String POSTGRES_PORT = "postgres_port";
    String POSTGRES_USER = "postgres_user";
  }

  @VisibleForTesting
  interface DefaultValues {
    boolean MAINTENANCE_MODE = false;
    int PORT = 3304;
    String POSTGRES_DATABASE = "ta_users";
    String POSTGRES_HOST = "localhost";
    int POSTGRES_PORT = 5432;
  }
}
