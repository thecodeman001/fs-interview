export const fetchMoleculeData = async (query) => {
  const response = await fetch(
    `/api/v1/patients/molecules/search?query=${query}`
  );
  if (!response.ok) {
    throw new Error('Failed to fetch molecules');
  }
  return response.json();
};
