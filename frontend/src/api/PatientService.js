export const fetchPatientData = async (id) => {
  const response = await fetch(`/api/v1/patients/${id}`);
  if (!response.ok) {
    throw new Error('Failed to fetch patient data');
  }
  return response.json();
};

export const fetchPatientAllergies = async (id) => {
  const response = await fetch(`/api/v1/patients/${id}/allergies`);
  if (!response.ok) {
    throw new Error('Failed to fetch allergies');
  }
  return response.json();
};

export const updatePatientAllergies = async (id, updatedAllergies) => {
  const response = await fetch(`/api/v1/patients/${id}/allergies`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(updatedAllergies),
  });

  if (!response.ok) {
    throw new Error('Failed to update allergies');
  }

  return response.json();
};
