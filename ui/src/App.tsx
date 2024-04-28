import { useEffect } from 'react';
import GameOfLifeComponent from './GameOfLifeComponent';
import useGameOfLife from './useGameOfLife'
import NewGameOfLife from './NewGameOfLife';

function App() {
  const {game, createGame, iterateGame, deleteGame} = useGameOfLife();

  useEffect(() => {
    const tid = setInterval(() => iterateGame(), 500);
    return () => clearInterval(tid);
  }, [iterateGame]);

  return (
    <>
      <h1>Game of Life</h1>

      {!game && <NewGameOfLife createGame={createGame} />}
      {game && <GameOfLifeComponent game={game} deleteGame={deleteGame} />}
    </>
  )
}

export default App
