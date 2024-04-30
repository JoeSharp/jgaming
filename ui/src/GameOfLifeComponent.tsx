import React from 'react';
import { GameOfLife } from './types';

import "./gol.css"
import CellComponent from './CellComponent';

interface Props {
  game: GameOfLife;
  deleteGame: () => void;
}

const GameOfLifeComponent: React.FC<Props> = ({game, deleteGame}) => {
  return (<div>
    <button onClick={deleteGame}>Delete Game</button>
    <table className='gol'>
      <tbody>
        {game.game.board.contents.map((row, ri) => (<tr key={ri}>
          {row.map((cell, ci) => <CellComponent key={ci} value={cell} />)}
        </tr>))}
      </tbody>
    </table>
  </div>)
}

export default GameOfLifeComponent;
