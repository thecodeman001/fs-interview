import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import ProfileItem from '../ProfileItem/ProfileItem';
import './PatientProfile.css';
import MoleculeSearch from '../MoleculeSearch/MoleculeSearch';
import './PatientProfile.css';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const PatientProfile = () => {
  const { id } = useParams();
  const [patientData, setPatientData] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [allergies, setAllergies] = useState([]); 

  const fetchData = () => {
    fetch(`/api/v1/patients/${id}`)
      .then((patientResponse) => {
        if (!patientResponse.ok) {
          throw new Error('Failed to fetch patient data');
        }
        return patientResponse.json();
      })
      .then((patientData) => {
        setPatientData(patientData);

        return fetch(`/api/v1/patients/${id}/allergies`);
      })
      .then((allergiesResponse) => {
        if (!allergiesResponse.ok) {
          throw new Error('Failed to fetch allergies');
        }
        return allergiesResponse.json();
      })
      .then((allergiesData) => {
        setAllergies(allergiesData);
      })
      .catch((error) => {
        setErrorMessage({
          title: 'Error fetching data',
          details: error.message,
        });
      });
  };

  useEffect(() => {
    fetchData();
  }, [id]);

  const addMoleculeToAllergies = async (moleculeId) => {
    if (!patientData.allergies) {
      patientData.allergies = [];
    }

    const existingAllergies = (patientData.allergies || []).map((allergy) =>
      typeof allergy === 'object' ? allergy.id : allergy
    );

    const combinedAllergies = [
      ...allergies.map((allergy) =>
        typeof allergy === 'object' ? allergy.id : allergy
      ),
      ...existingAllergies,
    ];

    if (combinedAllergies.includes(moleculeId)) {
      toast.info('This molecule is already added to the allergies list.');
      return;
    }

    const updatedAllergies = [...new Set([...combinedAllergies, moleculeId])];

    try {
      const response = await fetch(`/api/v1/patients/${id}/allergies`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedAllergies),
      });

      if (!response.ok) {
        throw new Error('Failed to update allergies');
      }

      const data = await response.json();
      setPatientData((prevState) => ({ ...prevState, allergies: data }));
      toast.success('Molecule successfully added to allergies');
    } catch (error) {
      console.error('Error updating allergies:', error);
      toast.error('Failed to update allergies. Please try again later.');
    }
  };

  if (errorMessage) {
    return (
      <div className='container'>
        <h1>{errorMessage.title}</h1>
        <p>{errorMessage.details}</p>
      </div>
    );
  }
  if (!patientData) {
    return <div className='container'>Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Patient Profile</h1>
      <div className="patient-profile">
        <ProfileItem label="ID" value={patientData.id}/>
        <ProfileItem label="First Name" value={patientData.first_name}/>
        <ProfileItem label="Last Name" value={patientData.last_name}/>
        <ProfileItem label="Birth Date" value={patientData.birth_date}/>
        <ProfileItem label="Height" value={patientData.height}/>
        <ProfileItem label="Weight" value={patientData.weight}/>

        <MoleculeSearch
          onMoleculeSelect={addMoleculeToAllergies}
          selectedMolecules={[...allergies, ...(patientData.allergies || [])]}
        />

      </div>
    </div>
  );
};

export default PatientProfile;
