import { useState, useCallback } from 'react';
import { GameOfLife, GameOfLifeApi } from './types';

function useGameOfLife(): GameOfLifeApi {
  const [game, setGame] = useState<GameOfLife | null>(null);

  const createGame = useCallback((name?: string) => {
    fetch(`http://localhost:8080/gol/create${name ? '/' + name : ''}`, { method: 'POST', mode: 'cors' })
      .then(r => r.json())
      .then(g => setGame(g));
  }, []);

  const iterateGame = useCallback(() => {
    if (!game) return;
    fetch(`http://localhost:8080/gol/iterate/${game.id}`, { method: 'POST', mode: 'cors' })
      .then(r => r.json())
      .then(g => setGame(g));
  }, [game]);

  const deleteGame = useCallback(() => {
    if (!game) return;
    fetch(`http://localhost:8080/gol/${game.id}`, { method: 'DELETE', mode: 'cors' })
      .then(() => setGame(null));
  }, [game]);

  return {
    game,
    createGame,
    iterateGame,
    deleteGame
  }
}

export default useGameOfLife;
