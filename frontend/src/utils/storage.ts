/**
 * Reads and normalizes product IDs stored in localStorage for cart hydration.
 *
 * @param key - Storage key containing the persisted product list.
 * @returns An array of numeric product ids to pass to the cart fetch action.
 */
export const getStoredPerfumeIds = (key: string = "perfumes"): number[] => {
    try {
        const rawValue: string | null = localStorage.getItem(key);

        if (!rawValue) {
            return [];
        }

        const parsedValue: unknown = JSON.parse(rawValue);

        if (!Array.isArray(parsedValue)) {
            return [];
        }

        return parsedValue
            .map((value) => Number(value))
            .filter((value) => !Number.isNaN(value));
    } catch (error) {
        console.warn("Unable to read localStorage values for cart hydration.", error);
        return [];
    }
};
