import { ChangeEventHandler, FC, useCallback, useState, useMemo } from 'react';
import GolPicker from './GolPicker';
import CellComponent from './CellComponent';

interface Props {
  createGame: (template?: string) => void;
}

const NewGameOfLife: FC<Props> = ({createGame}) => {
  const [template, setTemplate] = useState<string>('');
  const [custom, setCustom] = useState<boolean>(false);
  const [width, setWidth] = useState<number>(5);
  const [height, setHeight] = useState<number>(5);

  const onCustomChange: ChangeEventHandler<HTMLInputElement> = 
    useCallback(({target:{checked}}) => setCustom(checked), []);

  const onCustomWidthChange: ChangeEventHandler<HTMLInputElement> = 
    useCallback(({target:{value}}) => setWidth(parseInt(value, 10)), []);

  const onCustomHeightChange: ChangeEventHandler<HTMLInputElement> = 
    useCallback(({target:{value}}) => setHeight(parseInt(value, 10)), []);

  const customGrid: boolean[][] = useMemo(() => {
    const grid: boolean[][] = [];

    for (let i=0; i<width; i++) {
      const row: boolean[] = [];
      for (let j=0; j<height; j++) {
        row.push(false);
      }
      grid.push(row);
    }

    return grid;
  }, [width, height]);

  return (
    <>
      <button onClick={() => createGame(template)}>Create Game</button>
      <form>
        <label>Custom</label>
        <input type='checkbox' checked={custom} onChange={onCustomChange} />
        {!custom &&
          <>
            <label>Template Name</label>
            <GolPicker onChange={setTemplate} value={template} />
          </>
        }
        {custom &&
          <>
            <label>Width</label>
            <input type='number' min='5' max='100' value={width} onChange={onCustomWidthChange} />
            <label>Height</label>
            <input type='number' min='5' max='100' value={height} onChange={onCustomHeightChange} />
          </>
        }
      </form>

      {custom && <table className='gol'>
        <tbody>
        {customGrid.map((row, ri) => <tr key={ri}>
            {row.map((cell, ci) => <CellComponent key={ci} value={cell} />)}
          </tr>)}
        </tbody>
      </table>}
    </>
  );
}

export default NewGameOfLife;
