export interface ErrorMessage {
  field: string;
  text: string;
}

export function errorMessageFactory(field: string, text: string): ErrorMessage {
  return {
    field,
    text
  }
}
