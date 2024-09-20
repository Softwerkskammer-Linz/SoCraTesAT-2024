const { TicTacToe } = require("./tictactoe")

describe('Tic Tac Toe', () => {

	it('has a playing field', () => {
		// arrange

		// act
		const spielfeld = TicTacToe.spielfeld

		// assert
		expect(spielfeld).toBeDefined()
	})

	it('has a 3x3 playing field', () => {
		// act
		const spielfeld = TicTacToe.spielfeld

		// assert
		expect(spielfeld).toHaveLength(3)
		expect(spielfeld[0]).toHaveLength(3)
		expect(spielfeld[1]).toHaveLength(3)
		expect(spielfeld[2]).toHaveLength(3)
	})

	it('has an empty 3x3 playing field', () => {
		// arrange

		// act
		const spielfeld = TicTacToe.spielfeld

		// assert
		expect(spielfeld[0][0]).toBeUndefined()
		expect(spielfeld[0][1]).toBeUndefined()
		expect(spielfeld[0][2]).toBeUndefined()
		expect(spielfeld[1][0]).toBeUndefined()
		expect(spielfeld[1][1]).toBeUndefined()
		expect(spielfeld[1][2]).toBeUndefined()
		expect(spielfeld[2][0]).toBeUndefined()
		expect(spielfeld[2][1]).toBeUndefined()
		expect(spielfeld[2][2]).toBeUndefined()
	})


})
