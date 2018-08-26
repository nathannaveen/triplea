package games.strategy.sound;

import java.util.Collection;

import games.strategy.engine.data.PlayerID;

/**
 * Implementation of {@link ISound} that does nothing (i.e. no sounds will be played).
 */
public class HeadlessSoundChannel implements ISound {

  @Override
  public void playSoundForAll(final String clipName, final PlayerID playerId) {}

  @Override
  public void playSoundToPlayers(final String clipName, final Collection<PlayerID> playersToSendTo,
      final Collection<PlayerID> butNotThesePlayers, final boolean includeObservers) {}

}
