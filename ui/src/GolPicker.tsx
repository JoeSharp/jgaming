import { ChangeEventHandler, FC, useCallback, useEffect, useState } from 'react';

interface Props {
  value: string;
  onChange: (v: string) => void;
}

const GolPicker: FC<Props> = ({ value, onChange }) => {
  const [options, setOptions] = useState<string[]>([]);

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_HOST}/gol/template/names`, { method: 'GET', mode: 'cors' })
      .then(r => r.json())
      .then(g => setOptions(g));
  }, []);

  const onSelectChange: ChangeEventHandler<HTMLSelectElement> = useCallback(({target:{value}}) => {
    onChange(value);
  }, [onChange]);

  return <select value={value} onChange={onSelectChange}>
    {options.map(o => <option key={o} value={o}>{o}</option>)}
  </select>
}

export default GolPicker;
