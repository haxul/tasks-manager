export interface Member {
  username: string
  avatar: string
  isDeleted: boolean
  isBanned: boolean
  created: string
  isOnline: boolean
}


export interface ResponseGetAllChatMessage {
  from: string
  text: string
  time: number
}


export interface ChatMessage {
  isOutgoingMessage: boolean
  text: string
  date: string
}


export interface newIncomingMessage {
  text:string
  from:string
}
