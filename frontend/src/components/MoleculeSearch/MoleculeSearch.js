import React, { useState, useEffect, useRef, useCallback} from 'react';
import './MoleculeSearch.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { fetchMoleculeData } from '../../api/MoleculeService';

const MoleculeSearch = ({ onMoleculeSelect, selectedMolecules }) => {
  const [searchText, setSearchText] = useState('');
  const [molecules, setMolecules] = useState([]);
  const [showMoleculeList, setShowMoleculeList] = useState(false);
  const searchContainerRef = useRef(null);

  const searchMolecules = async (query) => {
    try {
      const data = await fetchMoleculeData(query);
      setMolecules(data || []);
    } catch (error) {
      console.error('Error fetching molecules:', error);
      toast.error('Failed to fetch molecules. Please try again later.');
    }
  };

  const handleSelect = (moleculeId) => {
    onMoleculeSelect(moleculeId);
  };

  const isSelected = useCallback(
    (moleculeId) => {
      return selectedMolecules.some((allergy) =>
        typeof allergy === 'object'
          ? allergy.id === moleculeId
          : allergy === moleculeId
      );
    },
    [selectedMolecules]
  );

  const handleClickOutside = useCallback((event) => {
    if (
      searchContainerRef.current &&
      !searchContainerRef.current.contains(event.target)
    ) {
      setShowMoleculeList(false);
    }
  }, []);

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleFocus = useCallback(() => {
    setShowMoleculeList(true);
    if (searchText === '') {
      searchMolecules('');
    }
  }, [searchText]);

  return (
    <div className='molecule-search' ref={searchContainerRef}>
      <input
        type='text'
        placeholder='Search for a molecule...'
        value={searchText}
        onFocus={handleFocus}
        onChange={(e) => {
          setSearchText(e.target.value);
          searchMolecules(e.target.value);
        }}
      />
      {showMoleculeList ? (
        <ul className='molecule-list'>
          {molecules.map((molecule) => (
            <li
              key={molecule.id}
              onClick={() => handleSelect(molecule.id)}
              className={isSelected(molecule.id) ? 'selected' : ''}
            >
              {molecule.name}
            </li>
          ))}
        </ul>
      ) : null}
      <ToastContainer />
    </div>
  );
};

export default MoleculeSearch;
